package vnua.kltn.herb.service;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.utils.PageUtils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class BaseSearchService<T, R> {
    protected Page<R> search(SearchDto searchDto, JpaRepository<T, ?> repository, JpaSpecificationExecutor<T> specExecutor, Function<T, R> mapper, List<String> searchableFields) {
        Specification<T> spec = createSpecification(searchDto, searchableFields);

        // Xử lý phân trang
        var pageable = createPageable(searchDto);

        // Thực hiện truy vấn
        Page<T> entities = specExecutor.findAll(spec, pageable);
        return entities.map(mapper);
    }

    protected Specification<T> createSpecification(SearchDto searchDto, List<String> searchableFields) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Xử lý từ khóa tìm kiếm
            if (searchDto.getKeyword() != null && !searchDto.getKeyword().isEmpty()) {
                String keyword = "%" + searchDto.getKeyword().toLowerCase() + "%";
                List<Predicate> fieldPredicates = new ArrayList<>();

                for (String field : searchableFields) {
                    try {
                        Path<?> path = root.get(field);
                        Class<?> fieldType = path.getJavaType();

                        if (String.class.isAssignableFrom(fieldType)) {
                            // Đối với trường String, sử dụng hàm lower()
                            fieldPredicates.add(criteriaBuilder.like(
                                    criteriaBuilder.lower(path.as(String.class)), keyword));
                        } else if (Number.class.isAssignableFrom(fieldType)) {
                            // Đối với Number, chuyển đổi thành String an toàn hơn
                            fieldPredicates.add(criteriaBuilder.like(
                                    criteriaBuilder.function("concat", String.class,
                                            criteriaBuilder.literal(""), path),
                                    keyword));
                        } else {
                            // Đối với các loại khác, cũng sử dụng concat
                            fieldPredicates.add(criteriaBuilder.like(
                                    criteriaBuilder.function("concat", String.class,
                                            criteriaBuilder.literal(""), path),
                                    keyword));
                        }
                    } catch (IllegalArgumentException e) {
                        // Bỏ qua trường không tồn tại
                        continue;
                    }
                }

                if (!fieldPredicates.isEmpty()) {
                    predicates.add(criteriaBuilder.or(fieldPredicates.toArray(new Predicate[0])));
                }

//                Predicate[] fieldPredicates = searchableFields.stream()
//                        .map(field -> criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), keyword))
//                        .toArray(Predicate[]::new);
//                predicates.add(criteriaBuilder.or(fieldPredicates));
            }

            // Xử lý các bộ lọc động
            if (searchDto.getFilters() != null && !searchDto.getFilters().isEmpty()) {
                for (Map.Entry<String, Object> filter : searchDto.getFilters().entrySet()) {
                    String key = filter.getKey();
                    Object value = filter.getValue();
                    Class<?> fieldType = root.get(key).getJavaType();

                    // Xử lý các loại filter khác nhau
                    if (value != null) {
                        if (value instanceof String) {
                            if (String.class.isAssignableFrom(fieldType)) {
                                predicates.add(criteriaBuilder.like(
                                        criteriaBuilder.lower(root.get(key)),
                                        "%" + ((String) value).toLowerCase() + "%"
                                ));
                            } else if (Number.class.isAssignableFrom(fieldType)) {
                                try {
                                    Number numberValue = NumberFormat.getInstance().parse((String) value);
                                    predicates.add(criteriaBuilder.equal(root.get(key), numberValue));
                                } catch (ParseException e) {
                                    predicates.add(criteriaBuilder.like(
                                            criteriaBuilder.function("cast", String.class, root.get(key), criteriaBuilder.literal("varchar")),
                                            "%" + ((String) value).toLowerCase() + "%"
                                    ));
                                }
                            } else {
                                predicates.add(criteriaBuilder.equal(root.get(key), value));
                            }
                        } else if (value instanceof List) {
                            // Xử lý filter là danh sách (ví dụ: filter theo nhiều ID)
                            predicates.add(root.get(key).in((List<?>) value));
                        } else {
                            // Xử lý các filter so sánh bằng
                            predicates.add(criteriaBuilder.equal(root.get(key), value));
                        }
                    }
                }
            }

            // Xử lý excludeId
            if (searchDto.getExcludeId() != null) {
                Class<?> idType = root.get("id").getJavaType(); // Giả sử ID là trường "id"
                if (Number.class.isAssignableFrom(idType)) {
                    try {
                        Number excludeId = NumberFormat.getInstance().parse(searchDto.getExcludeId().toString());
                        predicates.add(criteriaBuilder.notEqual(root.get("id"), excludeId));
                    } catch (ParseException e) {
                        predicates.add(criteriaBuilder.notEqual(root.get("id"), searchDto.getExcludeId()));
                    }
                } else {
                    predicates.add(criteriaBuilder.notEqual(root.get("id"), searchDto.getExcludeId()));
                }
            }

//            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            return predicates.isEmpty() ? criteriaBuilder.conjunction() : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    protected Pageable createPageable(SearchDto searchDto) {
        var pageable = PageUtils.getPageable(
                searchDto.getPageIndex() != null ? searchDto.getPageIndex() : 0,
                searchDto.getPageSize() != null ? searchDto.getPageSize() : 10
        );

        // Xử lý sắp xếp nếu có
        if (searchDto.getSortField() != null && !searchDto.getSortField().isEmpty()) {
            Sort.Direction direction = searchDto.getSortDirection() != null &&
                    searchDto.getSortDirection().equalsIgnoreCase("desc")
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;

            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, searchDto.getSortField()));

//            pageable = PageUtils.getPageable(
//                    pageable.getPageNumber(),
//                    pageable.getPageSize(),
//                    Sort.by(direction, searchDto.getSortField())
//            );
        }

        return pageable;
    }
}

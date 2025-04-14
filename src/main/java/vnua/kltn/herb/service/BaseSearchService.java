package vnua.kltn.herb.service;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.utils.PageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract  class BaseSearchService<T, R> {
    protected Page<R> search(SearchDto searchDto, JpaRepository<T, ?> repository, JpaSpecificationExecutor<T> specExecutor, Function<T, R> mapper, List<String> searchableFields) {
        Specification<T> spec = createSpecification(searchDto, searchableFields);

        // Xử lý phân trang
        var pageable = createPageable(searchDto);

        // Thực hiện truy vấn
        Page<T> entities = specExecutor.findAll(spec, pageable);
        return entities.map(mapper);
    }


    private Specification<T> createSpecification(SearchDto searchDto, List<String> searchableFields) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Xử lý từ khóa tìm kiếm
            if (searchDto.getKeyword() != null && !searchDto.getKeyword().isEmpty()) {
                String keyword = "%" + searchDto.getKeyword().toLowerCase() + "%";
                Predicate[] fieldPredicates = searchableFields.stream()
                        .map(field -> criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), keyword))
                        .toArray(Predicate[]::new);
                predicates.add(criteriaBuilder.or(fieldPredicates));
            }

            // Xử lý các bộ lọc động
            if (searchDto.getFilters() != null && !searchDto.getFilters().isEmpty()) {
                for (Map.Entry<String, Object> filter : searchDto.getFilters().entrySet()) {
                    String key = filter.getKey();
                    Object value = filter.getValue();

                    // Xử lý các loại filter khác nhau
                    if (value != null) {
                        if (value instanceof String) {
                            predicates.add(criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get(key)),
                                    "%" + ((String) value).toLowerCase() + "%"
                            ));
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

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Pageable createPageable(SearchDto searchDto) {
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

            pageable = PageUtils.getPageable(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(direction, searchDto.getSortField())
            );
        }

        return pageable;
    }
}

package vnua.kltn.herb.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.dto.request.PlantRequestDto;
import vnua.kltn.herb.dto.response.PlantResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.entity.Plant;
import vnua.kltn.herb.entity.PlantMedia;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.PlantMediaRepository;
import vnua.kltn.herb.repository.PlantRepository;
import vnua.kltn.herb.service.BaseSearchService;
import vnua.kltn.herb.service.PlantService;
import vnua.kltn.herb.service.mapper.PlantMapper;
import vnua.kltn.herb.utils.SlugGenerator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlantServiceImpl extends BaseSearchService<Plant, PlantResponseDto> implements PlantService  {
    private final PlantRepository plantRepo;
    private final PlantMapper plantMapper;
    private final PlantMediaRepository plantMediaRepo;

    @Override
    @Transactional
    public PlantResponseDto create(PlantRequestDto requestDto) throws HerbException {
        if (plantRepo.existsByName(requestDto.getName())
                || plantRepo.existsByScientificName(requestDto.getScientificName())) {
            throw new HerbException(ErrorCodeEnum.EXISTED_USERNAME);
        }

        var plantEntity = plantMapper.requestToEntity(requestDto);
        plantEntity.setSlug(SlugGenerator.generateSlug(plantEntity.getName()));
        plantRepo.save(plantEntity);
        return plantMapper.entityToResponse(plantEntity);
    }

    @Override
    public PlantResponseDto getById(Long id) throws HerbException {
        var plantEntity = plantRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        return plantMapper.entityToResponse(plantEntity);
    }

    /*
    @Override
    public Page<PlantResponseDto> search(SearchDto searchDto) {
        Specification<Plant> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Xử lý từ khóa tìm kiếm
            if (searchDto.getKeyword() != null && !searchDto.getKeyword().isEmpty()) {
                String keyword = "%" + searchDto.getKeyword().toLowerCase() + "%";
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), keyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("scientificName")), keyword),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), keyword)
                ));
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

        // Xử lý phân trang
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

        // Thực hiện truy vấn
        Page<Plant> plants = plantRepo.findAll(spec, pageable);
        return plants.map(plantMapper::entityToResponse);
    }

     */
    public Page<PlantResponseDto> search(SearchDto searchDto) {
        List<String> searchableFields = List.of("name", "scientificName", "description", "diseaseId");
        var plants = super.search(searchDto, plantRepo, plantRepo, plantMapper::entityToResponse, searchableFields);

        List<Long> plantIds = plants.getContent().stream().map(PlantResponseDto::getId).toList();

        var featuredMedias = plantMediaRepo.findByIdPlantIdInAndIsFeaturedTrue(plantIds);
        Map<Long, PlantMedia> mediaMap = featuredMedias.stream()
                .collect(Collectors.toMap(
                        pm -> pm.getId().getPlantId(),
                        pm -> pm,
                        (pm1, pm2) -> pm1 // Giữ bản ghi đầu tiên nếu trùng
                ));

        return plants.map(plant -> {
            var media = mediaMap.get(plant.getId());
            if (media != null) {
                plant.setFeaturedMediaId(media.getId().getMediaId());
            }
            return plant;
        });
    }

    @Override
    public Boolean update(Long id, PlantRequestDto requestDto) throws HerbException {
        var plant = plantRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));

        plantMapper.setValue(requestDto, plant);
        plantRepo.save(plant);
        return true;
    }
}

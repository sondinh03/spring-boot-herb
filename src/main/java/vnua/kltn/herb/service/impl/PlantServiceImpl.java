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

    public Page<PlantResponseDto> search(SearchDto searchDto) {
        List<String> searchableFields = List.of("id", "name", "scientificName", "description", "diseaseId", "familyId", "generaId", "chemicalComposition");
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

package vnua.kltn.herb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.dto.request.PlantRequestDto;
import vnua.kltn.herb.dto.response.PlantResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.entity.Plant;
import vnua.kltn.herb.entity.PlantImage;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.PlantImageRepository;
import vnua.kltn.herb.repository.PlantRepository;
import vnua.kltn.herb.service.PlantService;
import vnua.kltn.herb.service.mapper.PlantMapper;
import vnua.kltn.herb.utils.PageUtils;

@Service
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService {
    private final PlantRepository plantRepo;
    private final PlantMapper plantMapper;
    private final PlantImageRepository plantImageRepo;

    @Override
    @Transactional
    public PlantResponseDto create(PlantRequestDto requestDto) throws HerbException {
        validatePlant(requestDto, true);

        var plant = plantMapper.requestToEntity(requestDto);
        plantRepo.save(plant);

        // Lưu liên kết file và plant
        var imageIds = requestDto.getImageIds();
        if (imageIds != null && !imageIds.isEmpty()) {
            boolean isPrimarySet = false;
            for (Long imageId : imageIds) {
                var plantImageEntity = new PlantImage();
                plantImageEntity.setImageId(imageId);
                plantImageEntity.setPlantId(plant.getId());
                plantImageEntity.setIsPrimary(!isPrimarySet);
                plantImageRepo.save(plantImageEntity);
            }
        }
        return plantMapper.entityToResponse(plant);
    }

    private void validatePlant(PlantRequestDto requestDto, boolean isCreate) throws HerbException {
        Plant plant;
        if (isCreate) {
            plant = plantRepo.findByVietnameseName(requestDto.getVietnameseName());
        } else {
            plant = plantRepo.findByVietnameseNameAndNotId(requestDto.getVietnameseName(), requestDto.getId());
        }

        if (plant != null) {
            throw new HerbException(ErrorCodeEnum.PLAN_IS_EXIST);
        }
    }

    @Override
    public PlantResponseDto getById(Long id) throws HerbException {
        var planFamily = plantRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        return plantMapper.entityToResponse(planFamily);
    }

    @Override
    public Boolean update(Long id, PlantRequestDto requestDto) throws HerbException {
        var entity = plantRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        validatePlant(requestDto, false);
        plantMapper.setValue(requestDto, entity);
        plantRepo.save(entity);
        return true;
    }

    @Override
    public Boolean delete(Long id) throws HerbException {
        var plantFamily = plantRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        plantRepo.delete(plantFamily);
        return true;
    }

    public Page<PlantResponseDto> search(SearchDto dto) {
        Sort sort = Sort.unsorted();
        if (dto.getSortDirection() != null && dto.getSortField() != null) {
            sort = dto.getSortDirection().equalsIgnoreCase("desc")
                    ? Sort.by(dto.getSortField()).descending() : Sort.by(dto.getSortField()).ascending();
        }

        var pageRequest = PageUtils.getPageable(dto.getPageIndex(), dto.getPageSize(), sort);

        var plantEntities = plantRepo.search(dto.getKeyword(), pageRequest);
        return plantEntities.map(plantMapper::entityToResponse);
    }

    @Override
    public Page<PlantResponseDto> findByNameWithFirstLetter(SearchDto dto) {
        var pageRequest = PageUtils.getPageable(dto.getPageIndex(), dto.getPageSize());

        var plantEntities = plantRepo.findByNameWithFirstLetter(dto.getKeyword(), pageRequest);
        return plantEntities.map(plantMapper::entityToResponse);
    }
}

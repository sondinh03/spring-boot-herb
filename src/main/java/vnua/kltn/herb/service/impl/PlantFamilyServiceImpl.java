package vnua.kltn.herb.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.dto.request.PlantFamilyRequestDto;
import vnua.kltn.herb.dto.response.PlantFamilyResponseDto;
import vnua.kltn.herb.entity.PlantFamily;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.PlantFamilyRepository;
import vnua.kltn.herb.service.PlantFamilyService;
import vnua.kltn.herb.service.mapper.PlantFamilyMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class PlantFamilyServiceImpl implements PlantFamilyService {
    private final PlantFamilyRepository plantFamilyRepo;
    private final PlantFamilyMapper plantFamilyMapper;

    @Override
    public PlantFamilyResponseDto create(PlantFamilyRequestDto requestDto) throws HerbException {
        validatePlantFamily(requestDto, true);

        var planFamily = plantFamilyMapper.requestToEntity(requestDto);
        plantFamilyRepo.save(planFamily);
        return plantFamilyMapper.entityToResponse(planFamily);
    }

    private void validatePlantFamily(PlantFamilyRequestDto requestDto, boolean isCreate) throws HerbException {
        PlantFamily plantFamily;
        if (isCreate) {
            plantFamily = plantFamilyRepo.findByName(requestDto.getName());
        } else {
            plantFamily = plantFamilyRepo.findByNameAndNotId(requestDto.getName(), requestDto.getId());
        }

        if (plantFamily != null) {
            throw new HerbException(ErrorCodeEnum.PLAN_FAMILY_EXIST);
        }
    }

    @Override
    public PlantFamilyResponseDto getById(Long id) throws HerbException {
        var planFamily = plantFamilyRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        return plantFamilyMapper.entityToResponse(planFamily);
    }

    @Override

    public Boolean update(Long id, PlantFamilyRequestDto requestDto) throws HerbException {
        var entity = plantFamilyRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        validatePlantFamily(requestDto, false);
        plantFamilyMapper.setValue(requestDto, entity);
        plantFamilyRepo.save(entity);
        return true;
    }

    @Override
    public Boolean delete(Long id) throws HerbException {
        var plantFamily = plantFamilyRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        plantFamilyRepo.delete(plantFamily);
        return true;
    }

    @Override
    public List<PlantFamilyResponseDto> search(String searchText) {
        List<PlantFamily> plantFamilies;

        if (searchText == null || searchText.trim().isEmpty()) {
            plantFamilies = plantFamilyRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
        } else {
            var searchPattern = "%" + searchText + "%";
            plantFamilies = plantFamilyRepo.findByNameContainingIgnoreCase(searchPattern);
        }

        return plantFamilies.stream().map(plantFamilyMapper::entityToResponse).toList();
    }
}

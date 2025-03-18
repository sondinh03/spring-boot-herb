package vnua.kltn.herb.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.dto.request.DiseaseGroupRequestDto;
import vnua.kltn.herb.dto.response.DiseaseGroupResponseDto;
import vnua.kltn.herb.entity.DiseaseGroup;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.DiseaseGroupRepository;
import vnua.kltn.herb.service.DiseaseGroupService;
import vnua.kltn.herb.service.mapper.DiseaseGroupMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class DiseaseGroupServiceImpl implements DiseaseGroupService {
    private final DiseaseGroupRepository diseaseGroupRepo;
    private final DiseaseGroupMapper diseaseGroupMapper;

    @Override
    public DiseaseGroupResponseDto create(DiseaseGroupRequestDto requestDto) throws HerbException {
        validateDiseaseGroup(requestDto, true);

        var planFamily = diseaseGroupMapper.requestToEntity(requestDto);
        diseaseGroupRepo.save(planFamily);
        return diseaseGroupMapper.entityToResponse(planFamily);
    }


    private void validateDiseaseGroup(DiseaseGroupRequestDto requestDto, boolean isCreate) throws HerbException {
        DiseaseGroup diseaseGroup;
        if (isCreate) {
            diseaseGroup = diseaseGroupRepo.findByName(requestDto.getName());
        } else {
            diseaseGroup = diseaseGroupRepo.findByNameAndNotId(requestDto.getName(), requestDto.getId());
        }

        if (diseaseGroup != null) {
            throw new HerbException(ErrorCodeEnum.PLAN_FAMILY_EXIST);
        }
    }

    @Override
    public DiseaseGroupResponseDto getById(Long id) throws HerbException {
        var planFamily = diseaseGroupRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        return diseaseGroupMapper.entityToResponse(planFamily);
    }

    @Override
    public Boolean update(Long id, DiseaseGroupRequestDto requestDto) throws HerbException {
        var entity = diseaseGroupRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        validateDiseaseGroup(requestDto, false);
        diseaseGroupMapper.setValue(requestDto, entity);
        diseaseGroupRepo.save(entity);
        return true;
    }

    @Override
    public Boolean delete(Long id) throws HerbException {
        var DiseaseGroup = diseaseGroupRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        diseaseGroupRepo.delete(DiseaseGroup);
        return true;
    }

    @Override
    public List<DiseaseGroupResponseDto> search(String searchText) {
        List<DiseaseGroup> plantFamilies;

        if (searchText == null || searchText.trim().isEmpty()) {
            plantFamilies = diseaseGroupRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
        } else {
            var searchPattern = "%" + searchText + "%";
            plantFamilies = diseaseGroupRepo.findByNameContainingIgnoreCase(searchPattern);
        }

        return plantFamilies.stream().map(diseaseGroupMapper::entityToResponse).toList();
    }
}

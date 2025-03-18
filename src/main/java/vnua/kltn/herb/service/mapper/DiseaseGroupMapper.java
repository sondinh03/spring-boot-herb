package vnua.kltn.herb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vnua.kltn.herb.dto.request.DiseaseGroupRequestDto;
import vnua.kltn.herb.dto.request.PlantFamilyRequestDto;
import vnua.kltn.herb.dto.response.DiseaseGroupResponseDto;
import vnua.kltn.herb.dto.response.PlantFamilyResponseDto;
import vnua.kltn.herb.entity.DiseaseGroup;
import vnua.kltn.herb.entity.PlantFamily;

@Mapper(componentModel = "spring")
public interface DiseaseGroupMapper {
    DiseaseGroupResponseDto entityToResponse(DiseaseGroup entity);

    @Mapping(target = "id", ignore = true)
    DiseaseGroup requestToEntity(DiseaseGroupRequestDto dto);

    @Mapping(target = "id", ignore = true)
    void setValue(DiseaseGroupRequestDto requestDto, @MappingTarget DiseaseGroup entity);
}

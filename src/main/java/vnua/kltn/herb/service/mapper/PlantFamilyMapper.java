package vnua.kltn.herb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vnua.kltn.herb.dto.request.PlantFamilyRequestDto;
import vnua.kltn.herb.dto.response.PlantFamilyResponseDto;
import vnua.kltn.herb.entity.PlantFamily;

@Mapper(componentModel = "spring")
public interface PlantFamilyMapper {
    PlantFamilyResponseDto entityToResponse(PlantFamily entity);

    @Mapping(target = "id", ignore = true)
    PlantFamily requestToEntity(PlantFamilyRequestDto dto);

    @Mapping(target = "id", ignore = true)
    void setValue(PlantFamilyRequestDto requestDto, @MappingTarget PlantFamily entity);
}

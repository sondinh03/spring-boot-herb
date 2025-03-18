package vnua.kltn.herb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vnua.kltn.herb.dto.request.PlantRequestDto;
import vnua.kltn.herb.dto.response.PlantResponseDto;
import vnua.kltn.herb.entity.Plant;

@Mapper(componentModel = "spring")
public interface PlantMapper {
    PlantResponseDto entityToResponse(Plant entity);

    @Mapping(target = "id", ignore = true)
    Plant requestToEntity(PlantRequestDto dto);

    @Mapping(target = "id", ignore = true)
    void setValue(PlantRequestDto requestDto, @MappingTarget Plant entity);
}

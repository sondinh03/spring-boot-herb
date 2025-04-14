package vnua.kltn.herb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vnua.kltn.herb.dto.request.PlantRequestDto;
import vnua.kltn.herb.dto.response.PlantResponseDto;
import vnua.kltn.herb.entity.Plant;

@Mapper(componentModel = "spring")
public interface PlantMapper {
    PlantResponseDto entityToResponse(Plant plant);

    Plant requestToEntity(PlantRequestDto plantRequestDto);

    void setValue(PlantRequestDto requestDto,@MappingTarget Plant plant);
}

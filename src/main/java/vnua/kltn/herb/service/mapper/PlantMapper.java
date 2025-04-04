package vnua.kltn.herb.service.mapper;

import org.mapstruct.Mapper;
import vnua.kltn.herb.dto.UserDto;
import vnua.kltn.herb.dto.request.PlantRequestDto;
import vnua.kltn.herb.dto.response.PlantResponseDto;
import vnua.kltn.herb.entity.Plant;
import vnua.kltn.herb.entity.User;

@Mapper(componentModel = "spring")
public interface PlantMapper {
    PlantResponseDto entityToResponse(Plant plant);

    Plant requestToEntity(PlantRequestDto plantRequestDto);
}

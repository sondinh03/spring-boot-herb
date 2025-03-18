package vnua.kltn.herb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vnua.kltn.herb.dto.request.PlantImageRequestDto;
import vnua.kltn.herb.dto.response.PlantImageResponseDto;
import vnua.kltn.herb.entity.PlantFamily;
import vnua.kltn.herb.entity.PlantImage;

@Mapper(componentModel = "spring")
public interface PlantImageMapper {
    PlantImageResponseDto entityToResponse(PlantImage entity);

    @Mapping(target = "id", ignore = true)
    PlantImage requestToEntity(PlantImageRequestDto dto);

    @Mapping(target = "id", ignore = true)
    void setValue(PlantImageRequestDto requestDto, @MappingTarget PlantImage entity);
}

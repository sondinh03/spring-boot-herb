package vnua.kltn.herb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vnua.kltn.herb.dto.request.ImageRequestDto;
import vnua.kltn.herb.dto.response.ImageResponseDto;
import vnua.kltn.herb.entity.Image;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageResponseDto entityToResponse(Image entity);

    @Mapping(target = "id", ignore = true)
    Image requestToEntity(ImageRequestDto dto);

    @Mapping(target = "id", ignore = true)
    void setValue(ImageRequestDto requestDto, @MappingTarget Image entity);
}

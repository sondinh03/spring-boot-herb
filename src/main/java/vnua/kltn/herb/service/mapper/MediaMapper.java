package vnua.kltn.herb.service.mapper;

import org.mapstruct.Mapper;
import vnua.kltn.herb.dto.request.MediaRequestDto;
import vnua.kltn.herb.dto.response.MediaResponseDto;
import vnua.kltn.herb.entity.Media;

@Mapper(componentModel = "spring")
public interface MediaMapper {
    MediaResponseDto entityToResponse(Media Media);

    Media requestToEntity(MediaRequestDto MediaRequestDto);
}

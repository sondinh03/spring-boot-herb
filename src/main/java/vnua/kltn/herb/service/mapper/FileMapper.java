package vnua.kltn.herb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vnua.kltn.herb.dto.request.FileRequestDto;
import vnua.kltn.herb.dto.response.FileResponseDto;
import vnua.kltn.herb.dto.response.FileResponseDto;
import vnua.kltn.herb.entity.File;

@Mapper(componentModel = "spring")
public interface FileMapper {
    FileResponseDto entityToResponse(File entity);

    @Mapping(target = "id", ignore = true)
    File requestToEntity(FileRequestDto dto);

    @Mapping(target = "id", ignore = true)
    void setValue(FileRequestDto requestDto, @MappingTarget File entity);
}

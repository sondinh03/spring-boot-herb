package vnua.kltn.herb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vnua.kltn.herb.dto.request.GeneraRequestDto;
import vnua.kltn.herb.dto.response.GeneraResponseDto;
import vnua.kltn.herb.entity.Genera;

@Mapper(componentModel = "spring")
public interface GeneraMapper {
    GeneraResponseDto entityToResponse(Genera entity);

    Genera requestToEntity(GeneraRequestDto requestDto);

    void setValue(GeneraRequestDto requestDto, @MappingTarget Genera entity);
}

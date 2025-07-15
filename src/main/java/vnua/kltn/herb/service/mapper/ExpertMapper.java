package vnua.kltn.herb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vnua.kltn.herb.dto.request.ExpertRequestDto;
import vnua.kltn.herb.dto.response.ExpertResponseDto;
import vnua.kltn.herb.entity.Expert;

@Mapper(componentModel = "spring")
public interface ExpertMapper {
    ExpertResponseDto entityToResponse(Expert entity);

    Expert requestToEntity(ExpertRequestDto requestDto);

    void setValue(ExpertRequestDto requestDto, @MappingTarget Expert entity);
}

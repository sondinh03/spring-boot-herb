package vnua.kltn.herb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vnua.kltn.herb.dto.request.ResearchRequestDto;
import vnua.kltn.herb.dto.response.ResearchResponseDto;
import vnua.kltn.herb.entity.Research;

@Mapper(componentModel = "spring")
public interface ResearchMapper {
    ResearchResponseDto entityToResponse(Research research);

    Research requestToEntity(ResearchRequestDto requestDto);

    void setValue(ResearchRequestDto requestDto,@MappingTarget Research research);
}

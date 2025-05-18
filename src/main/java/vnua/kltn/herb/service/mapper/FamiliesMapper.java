package vnua.kltn.herb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vnua.kltn.herb.dto.request.FamiliesRequestDto;
import vnua.kltn.herb.dto.response.FamiliesResponseDto;
import vnua.kltn.herb.entity.Families;

@Mapper(componentModel = "spring")
public interface FamiliesMapper {
    FamiliesResponseDto entityToResponse(Families families);

    Families requestToEntity(FamiliesRequestDto requestDto);

    void setValue(FamiliesRequestDto requestDto, @MappingTarget Families families);
}

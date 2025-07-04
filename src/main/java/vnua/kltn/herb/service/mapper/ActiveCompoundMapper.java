package vnua.kltn.herb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vnua.kltn.herb.dto.request.ActiveCompoundRequestDto;
import vnua.kltn.herb.dto.response.ActiveCompoundResponseDto;
import vnua.kltn.herb.entity.ActiveCompound;

@Mapper(componentModel = "spring")
public interface ActiveCompoundMapper {
    ActiveCompoundResponseDto entityToResponse(ActiveCompound activeCompound);

    ActiveCompound requestToEntity(ActiveCompoundRequestDto requestDto);

    void setValue(ActiveCompoundRequestDto requestDto, @MappingTarget ActiveCompound entity);
}

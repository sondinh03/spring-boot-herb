package vnua.kltn.herb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vnua.kltn.herb.dto.request.DiseasesRequestDto;
import vnua.kltn.herb.dto.response.DiseasesResponseDto;
import vnua.kltn.herb.entity.Diseases;

@Mapper(componentModel = "spring")
public interface DiseasesMapper {
    DiseasesResponseDto entityToResponse(Diseases Diseases);

    Diseases requestToEntity(DiseasesRequestDto DiseasesRequestDto);

    void setValue(DiseasesRequestDto requestDto, @MappingTarget Diseases Diseases);
}

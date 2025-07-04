package vnua.kltn.herb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vnua.kltn.herb.dto.request.DataSourceRequestDto;
import vnua.kltn.herb.dto.response.DataSourceResponseDto;
import vnua.kltn.herb.entity.DataSource;

@Mapper(componentModel = "spring")
public interface DataSourceMapper {
    DataSourceResponseDto entityToResponse(DataSource dataSource);

    DataSource requestToEntity(DataSourceRequestDto requestDto);

    void setValue(DataSourceRequestDto requestDto,@MappingTarget DataSource dataSource);
}

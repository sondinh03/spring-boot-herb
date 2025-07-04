package vnua.kltn.herb.service;

import org.springframework.data.domain.Page;
import vnua.kltn.herb.dto.request.DataSourceRequestDto;
import vnua.kltn.herb.dto.request.MediaRequestDto;
import vnua.kltn.herb.dto.request.PlantRequestDto;
import vnua.kltn.herb.dto.response.DataSourceResponseDto;
import vnua.kltn.herb.dto.response.PlantResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;


public interface DataSourceService {
    DataSourceResponseDto create(DataSourceRequestDto dto) throws HerbException;

    DataSourceResponseDto getById(Long id) throws HerbException;

    Boolean update(Long id, DataSourceRequestDto dto) throws HerbException;

    Boolean delete(Long id) throws HerbException;

    Page<DataSourceResponseDto> search(SearchDto dto);
}

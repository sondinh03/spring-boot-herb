package vnua.kltn.herb.service;

import org.springframework.data.domain.Page;
import vnua.kltn.herb.dto.request.MediaRequestDto;
import vnua.kltn.herb.dto.request.PlantRequestDto;
import vnua.kltn.herb.dto.response.PlantResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;

import java.io.IOException;

public interface PlantService {
    PlantResponseDto create(PlantRequestDto requestDto) throws HerbException;

    PlantResponseDto getById(Long id) throws HerbException;

    Page<PlantResponseDto> search(SearchDto searchDto);

    Boolean update(Long id, PlantRequestDto requestDto) throws HerbException;

    Boolean uploadMedia(Long plantId, MediaRequestDto requestDto) throws HerbException, IOException;

    Long getTotal();
}

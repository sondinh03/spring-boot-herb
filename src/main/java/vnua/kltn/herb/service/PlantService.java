package vnua.kltn.herb.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vnua.kltn.herb.dto.request.PlantRequestDto;
import vnua.kltn.herb.dto.response.PlantResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;

public interface PlantService {
    PlantResponseDto create(PlantRequestDto requestDto) throws HerbException;

    PlantResponseDto findById(Long id);

    Page<PlantResponseDto> search(SearchDto searchDto);
}

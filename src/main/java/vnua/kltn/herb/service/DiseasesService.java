package vnua.kltn.herb.service;

import org.springframework.data.domain.Page;
import vnua.kltn.herb.dto.request.DiseasesRequestDto;
import vnua.kltn.herb.dto.response.DiseasesResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;

import java.util.List;

public interface DiseasesService {
    DiseasesResponseDto getById(Long id) throws HerbException;

    DiseasesResponseDto create(DiseasesRequestDto requestDto) throws HerbException;

    Boolean update(Long id, DiseasesRequestDto requestDto) throws HerbException;

    Page<DiseasesResponseDto> search(SearchDto searchDto);

    List<DiseasesResponseDto> getAll();
}

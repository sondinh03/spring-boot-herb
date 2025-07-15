package vnua.kltn.herb.service;

import org.springframework.data.domain.Page;
import vnua.kltn.herb.dto.request.MediaRequestDto;
import vnua.kltn.herb.dto.request.PlantRequestDto;
import vnua.kltn.herb.dto.request.ResearchRequestDto;
import vnua.kltn.herb.dto.response.PlantResponseDto;
import vnua.kltn.herb.dto.response.ResearchResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;

import java.io.IOException;

public interface ResearchService {
    ResearchResponseDto create(ResearchRequestDto requestDto) throws HerbException;

    ResearchResponseDto update(Long id, ResearchRequestDto requestDto) throws HerbException;

    ResearchResponseDto getById(Long id) throws HerbException;

    Page<ResearchResponseDto> search(SearchDto searchDto);
}

package vnua.kltn.herb.service;

import org.springframework.data.domain.Page;
import vnua.kltn.herb.dto.request.ExpertRequestDto;
import vnua.kltn.herb.dto.request.ResearchRequestDto;
import vnua.kltn.herb.dto.response.ExpertResponseDto;
import vnua.kltn.herb.dto.response.ResearchResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;

public interface ExpertService {
    ExpertResponseDto create(ExpertRequestDto requestDto) throws HerbException;

    Boolean update(Long id, ExpertRequestDto requestDto) throws HerbException;


    ExpertResponseDto getById(Long id) throws HerbException;

    Page<ExpertResponseDto> search(SearchDto searchDto);

    Long getTotal();
}

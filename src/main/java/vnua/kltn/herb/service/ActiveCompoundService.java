package vnua.kltn.herb.service;

import org.springframework.data.domain.Page;
import vnua.kltn.herb.dto.request.ActiveCompoundRequestDto;
import vnua.kltn.herb.dto.response.ActiveCompoundResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;

import java.util.List;

public interface ActiveCompoundService {
    ActiveCompoundResponseDto getById(Long id) throws HerbException;

    ActiveCompoundResponseDto create(ActiveCompoundRequestDto requestDto) throws HerbException;

    Boolean update(Long id, ActiveCompoundRequestDto requestDto) throws HerbException;

    Page<ActiveCompoundResponseDto> search(SearchDto searchDto);

    List<ActiveCompoundResponseDto> getAll();
}

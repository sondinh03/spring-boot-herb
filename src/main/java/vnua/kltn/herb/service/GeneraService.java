package vnua.kltn.herb.service;

import org.springframework.data.domain.Page;
import vnua.kltn.herb.dto.request.GeneraRequestDto;
import vnua.kltn.herb.dto.response.GeneraResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;

import java.util.List;

public interface GeneraService {
    GeneraResponseDto getById(Long id) throws HerbException;

    GeneraResponseDto create(GeneraRequestDto requestDto) throws HerbException;

    Boolean update(Long id, GeneraRequestDto requestDto) throws HerbException;

    Page<GeneraResponseDto> search(SearchDto searchDto);

    List<GeneraResponseDto> getAll();
}

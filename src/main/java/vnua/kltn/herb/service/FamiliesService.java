package vnua.kltn.herb.service;

import org.springframework.data.domain.Page;
import vnua.kltn.herb.dto.request.FamiliesRequestDto;
import vnua.kltn.herb.dto.response.FamiliesResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;

import java.util.List;

public interface FamiliesService {
    FamiliesResponseDto getById(Long id) throws HerbException;

    FamiliesResponseDto create(FamiliesRequestDto requestDto) throws HerbException;

    Boolean update(Long id, FamiliesRequestDto requestDto) throws HerbException;

    Page<FamiliesResponseDto> search(SearchDto searchDto);

    List<FamiliesResponseDto> getAll();
}

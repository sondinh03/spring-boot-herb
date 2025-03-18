package vnua.kltn.herb.service;

import vnua.kltn.herb.dto.request.DiseaseGroupRequestDto;
import vnua.kltn.herb.dto.response.DiseaseGroupResponseDto;
import vnua.kltn.herb.exception.HerbException;

import java.util.List;

public interface DiseaseGroupService {
    DiseaseGroupResponseDto create(DiseaseGroupRequestDto requestDto) throws HerbException;

    DiseaseGroupResponseDto getById(Long id) throws HerbException;

    Boolean update(Long id, DiseaseGroupRequestDto requestDto) throws HerbException;

    Boolean delete(Long id) throws HerbException;

    List<DiseaseGroupResponseDto> search(String searchText);
}

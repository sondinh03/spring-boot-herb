package vnua.kltn.herb.service;

import vnua.kltn.herb.dto.request.PlantFamilyRequestDto;
import vnua.kltn.herb.dto.response.PlantFamilyResponseDto;
import vnua.kltn.herb.exception.HerbException;

import java.util.List;

public interface PlantFamilyService {
    PlantFamilyResponseDto create(PlantFamilyRequestDto requestDto) throws HerbException;

    PlantFamilyResponseDto getById(Long id) throws HerbException;

    Boolean update(Long id, PlantFamilyRequestDto requestDto) throws HerbException;

    Boolean delete(Long id) throws HerbException;

    List<PlantFamilyResponseDto> search(String searchText);
}

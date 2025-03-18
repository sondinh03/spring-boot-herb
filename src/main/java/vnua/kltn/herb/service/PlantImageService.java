package vnua.kltn.herb.service;

import vnua.kltn.herb.dto.request.PlantImageRequestDto;
import vnua.kltn.herb.dto.request.PlantRequestDto;
import vnua.kltn.herb.dto.response.PlantImageResponseDto;
import vnua.kltn.herb.entity.PlantImage;
import vnua.kltn.herb.exception.HerbException;

public interface PlantImageService {
    PlantImage create(PlantImageRequestDto requestDto) throws HerbException;
}

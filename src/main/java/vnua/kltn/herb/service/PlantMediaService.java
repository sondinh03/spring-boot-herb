package vnua.kltn.herb.service;

import vnua.kltn.herb.dto.request.MediaRequestDto;
import vnua.kltn.herb.dto.response.MediaResponseDto;
import vnua.kltn.herb.exception.HerbException;

import java.io.IOException;
import java.util.List;

public interface PlantMediaService {
    List<Long> findMediaIdsByPlantId(Long id);

    List<MediaResponseDto> findMediaByPlantId(Long id) throws HerbException;

    Long findMediaIdFeaturedByPlantId(Long id);
}

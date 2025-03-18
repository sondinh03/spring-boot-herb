package vnua.kltn.herb.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.dto.request.PlantImageRequestDto;
import vnua.kltn.herb.dto.request.PlantRequestDto;
import vnua.kltn.herb.dto.response.PlantImageResponseDto;
import vnua.kltn.herb.entity.PlantImage;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.FileRepository;
import vnua.kltn.herb.repository.PlantImageRepository;
import vnua.kltn.herb.repository.PlantRepository;
import vnua.kltn.herb.service.PlantImageService;
import vnua.kltn.herb.service.mapper.PlantImageMapper;

@Service
@AllArgsConstructor
public class PlantImageServiceImpl implements PlantImageService {
    private final PlantImageRepository plantImageRepo;
    private final PlantRepository plantRepository;
    private final FileRepository fileRepo;
    private final PlantImageMapper plantImageMapper;

    @Override
    public PlantImage create(PlantImageRequestDto requestDto) throws HerbException {
        if (!validatePlantImage(requestDto)) {
            throw new HerbException(ErrorCodeEnum.NOT_FOUND);
        }

        var plantImage = new PlantImage();
        plantImage.setPlantId(requestDto.getPlantId());
        plantImage.setFileId(requestDto.getFileId());
        return plantImageRepo.save(plantImage);
    }

    private Boolean validatePlantImage(PlantImageRequestDto requestDto) {
        if (!plantRepository.existsById(requestDto.getPlantId())) {
            return false;
        }
        return fileRepo.existsById(requestDto.getFileId());
    }
}

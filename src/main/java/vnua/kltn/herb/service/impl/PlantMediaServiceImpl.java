package vnua.kltn.herb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.dto.request.MediaRequestDto;
import vnua.kltn.herb.dto.response.MediaResponseDto;
import vnua.kltn.herb.entity.Media;
import vnua.kltn.herb.entity.PlantMedia;
import vnua.kltn.herb.entity.PlantMediaId;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.MediaRepository;
import vnua.kltn.herb.repository.PlantMediaRepository;
import vnua.kltn.herb.repository.UserRepository;
import vnua.kltn.herb.service.MediaService;
import vnua.kltn.herb.service.PlantMediaService;
import vnua.kltn.herb.service.UserService;
import vnua.kltn.herb.service.mapper.MediaMapper;
import vnua.kltn.herb.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

import static vnua.kltn.herb.constant.enums.FileTypeEnum.*;

@Service
@RequiredArgsConstructor
public class PlantMediaServiceImpl implements PlantMediaService {
    private final PlantMediaRepository plantMediaRepo;

    @Override
    public List<Long> findMediaIdsByPlantId(Long id) {
        return plantMediaRepo.findMediaIdsByPlantId(id);
    }

    @Override
    public Long findMediaIdFeaturedByPlantId(Long id) {
        return plantMediaRepo.findMediaIdFeaturedByPlantId(id);
    }
}

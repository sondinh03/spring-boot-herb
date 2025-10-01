package vnua.kltn.herb.service.impl;

import lombok.AllArgsConstructor;
import org.hibernate.HibernateError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import vnua.kltn.herb.constant.enums.DataSourceTypeEnum;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.dto.request.MediaRequestDto;
import vnua.kltn.herb.dto.request.PlantRequestDto;
import vnua.kltn.herb.dto.response.PlantResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.entity.Plant;
import vnua.kltn.herb.entity.PlantMedia;
import vnua.kltn.herb.entity.PlantMediaId;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.FamiliesRepository;
import vnua.kltn.herb.repository.PlantMediaRepository;
import vnua.kltn.herb.repository.PlantRepository;
import vnua.kltn.herb.service.*;
import vnua.kltn.herb.service.mapper.PlantMapper;
import vnua.kltn.herb.utils.SlugGenerator;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlantServiceImpl extends BaseSearchService<Plant, PlantResponseDto> implements PlantService  {
    private final PlantRepository plantRepo;
    private final PlantMapper plantMapper;
    private final PlantMediaRepository plantMediaRepo;
    private final MediaService mediaService;
    private final UserService userService;
    private final DataSourceService dataSourceService;
    private final ActiveCompoundService activeCompoundService;
    private final FamiliesRepository familyRepo;

    private static final Logger logger = LoggerFactory.getLogger(PlantServiceImpl.class);

    @Override
    @Transactional
    public PlantResponseDto create(PlantRequestDto requestDto) throws HerbException {
        if (plantRepo.existsByName(requestDto.getName())
                || plantRepo.existsByScientificName(requestDto.getScientificName())) {
            throw new HerbException(ErrorCodeEnum.EXISTED_PLANT_NAME);
        }

        var plantEntity = plantMapper.requestToEntity(requestDto);
        plantEntity.setSlug(SlugGenerator.generateSlug(plantEntity.getName()));
        plantRepo.save(plantEntity);
        return plantMapper.entityToResponse(plantEntity);
    }

    @Override
    public PlantResponseDto getById(Long id) throws HerbException {
        var plantDetails = plantRepo.findByIdWithDetails(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        var plant = plantDetails.getPlant();

        var plantResponse = plantMapper.entityToResponse(plantDetails.getPlant());

        // Populate family name từ familyId
        if (plant.getFamilyId() != null) {
            familyRepo.findById(plant.getFamilyId())
                    .ifPresent(family -> plantResponse.setFamily(family.getName()));
        }

        // Populate genus name từ generaId
//        if (plant.getGeneraId() != null) {
//            generaRepo.findById(plant.getGeneraId())
//                    .ifPresent(genera -> response.setGenus(genera.getName()));
//        }

        // Enrich with data source
        var dataSource = plantDetails.getDataSource();
        if (dataSource != null) {
            plantResponse.setSourceName(dataSource.getName());
            plantResponse.setSourceAuthor(dataSource.getAuthor());
            plantResponse.setSourcePublicationYear(dataSource.getPublicationYear());
            plantResponse.setSourcePublisher(dataSource.getPublisher());

            var typeSourceEnum = DataSourceTypeEnum.fromType(dataSource.getTypeSource());
            plantResponse.setSourceType(typeSourceEnum.getDescription());
        }

        // Enrich with active compound
        var activeCompound = plantDetails.getActiveCompound();
        if (activeCompound != null) {
            plantResponse.setActiveCompound(activeCompound.getName());
        }

        return plantResponse;
    }

    public Page<PlantResponseDto> search(SearchDto searchDto) {
        List<String> searchableFields = List.of("id", "name", "scientificName", "description", "diseaseId", "familyId", "generaId", "chemicalComposition", "medicinalUses");
        var plants = super.search(searchDto, plantRepo, plantRepo, plantMapper::entityToResponse, searchableFields);

        List<Long> plantIds = plants.getContent().stream().map(PlantResponseDto::getId).toList();

        var featuredMedias = plantMediaRepo.findByIdPlantIdInAndIsFeaturedTrue(plantIds);
        Map<Long, PlantMedia> mediaMap = featuredMedias.stream()
                .collect(Collectors.toMap(
                        pm -> pm.getId().getPlantId(),
                        pm -> pm,
                        (pm1, pm2) -> pm1 // Giữ bản ghi đầu tiên nếu trùng
                ));

        return plants.map(plant -> {
            var media = mediaMap.get(plant.getId());
            if (media != null) {
                plant.setFeaturedMediaId(media.getId().getMediaId());
            }
            return plant;
        });
    }

    @Override
    public Boolean update(Long id, PlantRequestDto requestDto) throws HerbException {
        var plant = plantRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));

        plantMapper.setValue(requestDto, plant);
        plantRepo.save(plant);
        return true;
    }

    @Override
    @Transactional
    public Boolean uploadMedia(Long plantId, MediaRequestDto requestDto) throws HerbException, IOException {
        logger.info("Starting media upload for plantId: {} by user: {}", plantId, userService.getCurrentUser().getId());

        // Validate plantId exists first
        if (!plantRepo.existsById(plantId)) {
            throw new HerbException(ErrorCodeEnum.NOT_FOUND);
        }

        var mediaResponse = mediaService.upload(requestDto);
        if (mediaResponse == null || mediaResponse.getId() == null) {
            logger.error("Invalid media response for plantId: {}", plantId);
            throw new HerbException(ErrorCodeEnum.INTERNAL_SERVER_ERROR, "Không thể lấy thông tin media");
        }

        // Xác định isFeatured
        boolean isFeatured = Optional.ofNullable(requestDto.getIsFeatured())
                .orElseGet(() -> !plantMediaRepo.existsById_PlantId(plantId));

        // Tạo và lưu PlantMedia
        var plantMedia = PlantMedia.builder()
                .id(new PlantMediaId(plantId, mediaResponse.getId()))
                .isFeatured(isFeatured)
                .build();

        try {
            plantMediaRepo.save(plantMedia);
            logger.info("Successfully linked media {} to plant {}", mediaResponse.getId(), plantId);
            return true;
        } catch (Exception e) {
            logger.error("Failed to save PlantMedia for plantId {} and mediaId {}: {}",
                    plantId, mediaResponse.getId(), e.getMessage(), e);
            throw new HerbException(ErrorCodeEnum.FILE_DATABASE_ERROR, "Lỗi khi lưu liên kết PlantMedia");
        }
    }

    @Override
    public Long getTotal() {
        return plantRepo.count();
    }
}

package vnua.kltn.herb.service.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.dto.request.MediaRequestDto;
import vnua.kltn.herb.dto.request.PlantRequestDto;
import vnua.kltn.herb.dto.request.ResearchRequestDto;
import vnua.kltn.herb.dto.response.PlantResponseDto;
import vnua.kltn.herb.dto.response.ResearchResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.entity.Plant;
import vnua.kltn.herb.entity.PlantMedia;
import vnua.kltn.herb.entity.PlantMediaId;
import vnua.kltn.herb.entity.Research;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.MediaRepository;
import vnua.kltn.herb.repository.PlantMediaRepository;
import vnua.kltn.herb.repository.PlantRepository;
import vnua.kltn.herb.repository.ResearchRepository;
import vnua.kltn.herb.service.*;
import vnua.kltn.herb.service.mapper.PlantMapper;
import vnua.kltn.herb.service.mapper.ResearchMapper;
import vnua.kltn.herb.utils.SlugGenerator;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static vnua.kltn.herb.constant.enums.ErrorCodeEnum.*;
import static vnua.kltn.herb.utils.SlugGenerator.generateSlug;

@Service
@AllArgsConstructor
public class ResearchServiceImpl extends BaseSearchService<Research, ResearchResponseDto> implements ResearchService {
    private final ResearchRepository researchRepo;
    private final ResearchMapper researchMapper;
    private final MediaRepository mediaRepo;

    private static final Logger logger = LoggerFactory.getLogger(ResearchServiceImpl.class);
    private final SlugGenerator slugGenerator;

    private void validateCreateRequest(ResearchRequestDto requestDto) throws HerbException {
        // Basic null check (nếu không dùng Bean Validation)
        if (requestDto == null) {
            throw new HerbException(ErrorCodeEnum.INVALID_REQUEST);
        }

        // Business logic validations
        validateTitleUniqueness(requestDto.getTitle());
        validateMediaExists(requestDto.getMediaId());
    }

    private void validateUpdateRequest(Long id, ResearchRequestDto requestDto) throws HerbException {
        if (requestDto == null) {
            throw new HerbException(ErrorCodeEnum.INVALID_REQUEST);
        }

        // For update, check uniqueness excluding current record
        if (requestDto.getTitle() != null) {
            validateTitleUniquenessForUpdate(id, requestDto.getTitle());
        }

        if (requestDto.getMediaId() != null) {
            validateMediaExists(requestDto.getMediaId());
        }
    }

    // Business logic validations
    private void validateTitleUniqueness(String title) throws HerbException {
        if (title != null && researchRepo.existsByTitle(title.trim())) {
            throw new HerbException(ErrorCodeEnum.TITLE_EXISTS);
        }
    }

    private void validateTitleUniquenessForUpdate(Long id, String title) throws HerbException {
        if (title != null && researchRepo.existsByTitleAndIdNot(title.trim(), id)) {
            throw new HerbException(ErrorCodeEnum.TITLE_EXISTS);
        }
    }

    private void validateMediaExists(Long mediaId) throws HerbException {
        if (mediaId != null && !mediaRepo.existsById(mediaId)) {
            throw new HerbException(ErrorCodeEnum.MEDIA_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public ResearchResponseDto create(ResearchRequestDto requestDto) throws HerbException {
        validateCreateRequest(requestDto);

        var research = researchMapper.requestToEntity(requestDto);
        research.setSlug(generateSlug(requestDto.getTitle()));
        var savedResearch = researchRepo.save(research);
        var response = researchMapper.entityToResponse(savedResearch);

        if (savedResearch.getMediaId() != null) {
            var media = mediaRepo.findById(savedResearch.getMediaId()).orElseThrow(() -> new HerbException(MEDIA_NOT_FOUND));
            response.setMediaUrl(media.getUrlFile());
        }

        return response;
    }

    @Override
    @Transactional
    public ResearchResponseDto update(Long id, ResearchRequestDto requestDto) throws HerbException {
        validateUpdateRequest(id, requestDto);
        var existingResearch = researchRepo.findById(id).orElseThrow(() -> new HerbException(RESEARCH_NOT_FOUND));

        researchMapper.setValue(requestDto, existingResearch);
        researchRepo.save(existingResearch);
        var response = researchMapper.entityToResponse(existingResearch);

        if (existingResearch.getMediaId() != null) {
            var media = mediaRepo.findById(existingResearch.getMediaId()).orElseThrow(() -> new HerbException(MEDIA_NOT_FOUND));
            response.setMediaUrl(media.getUrlFile());
        }

        return response;
    }


    @Override
    public ResearchResponseDto getById(Long id) throws HerbException {
        if (id == null) {
            throw new HerbException(INVALID_REQUEST);
        }

        var research = researchRepo.findById(id).orElseThrow(() -> new HerbException(RESEARCH_NOT_FOUND));
        var response = researchMapper.entityToResponse(research);

        if (research.getMediaId() != null) {
            var media = mediaRepo.findById(research.getMediaId()).orElseThrow(() -> new HerbException(NOT_FOUND));
            response.setMediaUrl(media.getUrlFile());
        }

        return response;
    }



    @Override
    public Page<ResearchResponseDto> search(SearchDto searchDto) {
        List<String> searchableFields = List.of("id", "title", "content", "journal");

        return super.search(searchDto, researchRepo, researchRepo, researchMapper::entityToResponse, searchableFields);
    }

}

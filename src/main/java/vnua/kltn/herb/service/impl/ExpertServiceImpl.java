package vnua.kltn.herb.service.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.dto.request.ExpertRequestDto;
import vnua.kltn.herb.dto.request.ResearchRequestDto;
import vnua.kltn.herb.dto.response.ExpertResponseDto;
import vnua.kltn.herb.dto.response.GeneraResponseDto;
import vnua.kltn.herb.dto.response.ResearchResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.entity.Expert;
import vnua.kltn.herb.entity.Research;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.ExpertRepository;
import vnua.kltn.herb.repository.MediaRepository;
import vnua.kltn.herb.repository.ResearchRepository;
import vnua.kltn.herb.service.BaseSearchService;
import vnua.kltn.herb.service.ExpertService;
import vnua.kltn.herb.service.ResearchService;
import vnua.kltn.herb.service.mapper.ExpertMapper;
import vnua.kltn.herb.service.mapper.ResearchMapper;
import vnua.kltn.herb.utils.SlugGenerator;

import java.util.List;

import static vnua.kltn.herb.constant.enums.ErrorCodeEnum.FILE_DATABASE_ERROR;
import static vnua.kltn.herb.constant.enums.ErrorCodeEnum.NOT_FOUND;

@Service
@AllArgsConstructor
public class ExpertServiceImpl extends BaseSearchService<Expert, ExpertResponseDto> implements ExpertService {
    private final ExpertRepository expertRepo;
    private final ExpertMapper expertMapper;
    private final MediaRepository mediaRepo;

    @Override
    @Transactional
    public ExpertResponseDto create(ExpertRequestDto requestDto) throws HerbException {
        if (expertRepo.existsByName(requestDto.getName())) {
            throw new HerbException(ErrorCodeEnum.NAME_EXISTS);
        }

        var expertEntity = expertMapper.requestToEntity(requestDto);
        expertEntity.setSlug(SlugGenerator.generateSlug(requestDto.getTitle()));

        try {
            expertRepo.save(expertEntity);
            return expertMapper.entityToResponse(expertEntity);
        } catch (Exception e) {
            throw new HerbException(FILE_DATABASE_ERROR, e);
        }
    }

    @Override
    public ExpertResponseDto getById(Long id) throws HerbException {
        var expert = expertRepo.findById(id).orElseThrow(() -> new HerbException(NOT_FOUND));
        return expertMapper.entityToResponse(expert);
    }

    @Override
    public Page<ExpertResponseDto> search(SearchDto searchDto) {
        List<String> searchableFields = List.of("name", "slug");
        return super.search(searchDto, expertRepo, expertRepo, expertMapper::entityToResponse, searchableFields);
    }
}

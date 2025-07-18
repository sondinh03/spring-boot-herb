package vnua.kltn.herb.service.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.dto.request.ResearchPurchaseRequestDto;
import vnua.kltn.herb.dto.request.ResearchRequestDto;
import vnua.kltn.herb.dto.response.ResearchPurchaseResponseDto;
import vnua.kltn.herb.dto.response.ResearchResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.entity.Research;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.MediaRepository;
import vnua.kltn.herb.repository.ResearchPurchaseRepository;
import vnua.kltn.herb.repository.ResearchRepository;
import vnua.kltn.herb.service.BaseSearchService;
import vnua.kltn.herb.service.ResearchPurchaseService;
import vnua.kltn.herb.service.ResearchService;
import vnua.kltn.herb.service.UserService;
import vnua.kltn.herb.service.mapper.ResearchMapper;
import vnua.kltn.herb.service.mapper.ResearchPurchaseMapper;
import vnua.kltn.herb.utils.SlugGenerator;

import java.util.List;

import static vnua.kltn.herb.constant.enums.ErrorCodeEnum.*;
import static vnua.kltn.herb.utils.SlugGenerator.generateSlug;

@Service
@AllArgsConstructor
public class ResearchPurchaseServiceImpl implements ResearchPurchaseService {
    private final ResearchPurchaseRepository researchPurchaseRepo;
    private final ResearchPurchaseMapper researchPurchaseMapper;
    private final UserService userService;

    @Override
    public ResearchPurchaseResponseDto create(ResearchPurchaseRequestDto dto) {
        var entity = researchPurchaseMapper.requestToEntity(dto);
        researchPurchaseRepo.save(entity);
        return researchPurchaseMapper.entityToResponse(entity);
    }

    @Override
    public ResearchPurchaseResponseDto getByResearchIdAndUserId(Long researchId) throws HerbException {
        var user = userService.getCurrentUser();
        var userId = user.getId();
        var researchPurchase = researchPurchaseRepo.findByResearchIdAndUserId(researchId, userId);
        return researchPurchaseMapper.entityToResponse(researchPurchase);
    }
}

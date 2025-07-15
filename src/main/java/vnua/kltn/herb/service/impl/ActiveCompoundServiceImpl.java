package vnua.kltn.herb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.dto.request.ActiveCompoundRequestDto;
import vnua.kltn.herb.dto.response.ActiveCompoundResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.entity.ActiveCompound;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.ActiveCompoundRepository;
import vnua.kltn.herb.service.BaseSearchService;
import vnua.kltn.herb.service.ActiveCompoundService;
import vnua.kltn.herb.service.mapper.ActiveCompoundMapper;
import vnua.kltn.herb.utils.SlugGenerator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActiveCompoundServiceImpl extends BaseSearchService<ActiveCompound, ActiveCompoundResponseDto> implements ActiveCompoundService {
    private final ActiveCompoundRepository activeCompoundRepo;
    private final ActiveCompoundMapper activeCompoundMapper;

    @Override
    public ActiveCompoundResponseDto getById(Long id) throws HerbException {
        var entity = activeCompoundRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        return activeCompoundMapper.entityToResponse(entity);
    }

    @Override
    public ActiveCompoundResponseDto create(ActiveCompoundRequestDto requestDto) throws HerbException {
        if (activeCompoundRepo.existsByName(requestDto.getName())) {
            throw  new HerbException(ErrorCodeEnum.COMPOUND_EXISTS);
        }

        var entity = activeCompoundMapper.requestToEntity(requestDto);
        entity.setSlug(SlugGenerator.generateSlug(requestDto.getName()));
        activeCompoundRepo.save(entity);
        return activeCompoundMapper.entityToResponse(entity);
    }


    @Override
    public Boolean update(Long id, ActiveCompoundRequestDto requestDto) throws HerbException {
        var entity = activeCompoundRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));

        activeCompoundMapper.setValue(requestDto, entity);
        entity.setSlug(SlugGenerator.generateSlug(requestDto.getName()));
        activeCompoundRepo.save(entity);
        return true;
    }

    @Override
    public Page<ActiveCompoundResponseDto> search(SearchDto searchDto) {
        List<String> searchableFields = List.of("name", "slug");
        return super.search(searchDto, activeCompoundRepo, activeCompoundRepo, activeCompoundMapper::entityToResponse, searchableFields);
    }

    @Override
    public List<ActiveCompoundResponseDto> getAll() {
        var entityList = activeCompoundRepo.findAll();
        return entityList.stream().map(activeCompoundMapper::entityToResponse).toList();
    }
}

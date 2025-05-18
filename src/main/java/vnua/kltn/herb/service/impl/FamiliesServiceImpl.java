package vnua.kltn.herb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.dto.request.FamiliesRequestDto;
import vnua.kltn.herb.dto.response.FamiliesResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.entity.Families;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.FamiliesRepository;
import vnua.kltn.herb.service.BaseSearchService;
import vnua.kltn.herb.service.FamiliesService;
import vnua.kltn.herb.service.mapper.FamiliesMapper;
import vnua.kltn.herb.utils.SlugGenerator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FamiliesServiceImpl extends BaseSearchService<Families, FamiliesResponseDto> implements FamiliesService {
    private final FamiliesRepository familiesRepo;
    private final FamiliesMapper familiesMapper;

    @Override
    public FamiliesResponseDto getById(Long id) throws HerbException {
        var disease = familiesRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        return familiesMapper.entityToResponse(disease);
    }

    @Override
    public FamiliesResponseDto create(FamiliesRequestDto requestDto) throws HerbException {
        if (familiesRepo.existsByName(requestDto.getName())) {
            throw  new HerbException(ErrorCodeEnum.EXISTED_USERNAME);
        }

        var FamiliesEntity = familiesMapper.requestToEntity(requestDto);
        FamiliesEntity.setSlug(SlugGenerator.generateSlug(requestDto.getName()));
        familiesRepo.save(FamiliesEntity);
        return familiesMapper.entityToResponse(FamiliesEntity);
    }


    @Override
    public Boolean update(Long id, FamiliesRequestDto requestDto) throws HerbException {
        var disease = familiesRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));

        familiesMapper.setValue(requestDto, disease);
        familiesRepo.save(disease);
        return true;
    }


    @Override
    public Page<FamiliesResponseDto> search(SearchDto searchDto) {
        List<String> searchableFields = List.of("name", "slug");
        return super.search(searchDto, familiesRepo, familiesRepo, familiesMapper::entityToResponse, searchableFields);
    }

    @Override
    public List<FamiliesResponseDto> getAll() {
        var FamiliesEntityList = familiesRepo.findAll();
        return FamiliesEntityList.stream().map(familiesMapper::entityToResponse).toList();
    }
}

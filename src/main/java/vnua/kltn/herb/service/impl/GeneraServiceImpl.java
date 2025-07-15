package vnua.kltn.herb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.dto.request.GeneraRequestDto;
import vnua.kltn.herb.dto.response.GeneraResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.entity.Genera;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.GeneraRepository;
import vnua.kltn.herb.service.BaseSearchService;
import vnua.kltn.herb.service.GeneraService;
import vnua.kltn.herb.service.mapper.GeneraMapper;
import vnua.kltn.herb.utils.SlugGenerator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneraServiceImpl extends BaseSearchService<Genera, GeneraResponseDto> implements GeneraService {
    private final GeneraRepository generaRepo;
    private final GeneraMapper GeneraMapper;

    @Override
    public GeneraResponseDto getById(Long id) throws HerbException {
        var disease = generaRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        return GeneraMapper.entityToResponse(disease);
    }

    @Override
    public GeneraResponseDto create(GeneraRequestDto requestDto) throws HerbException {
        if (generaRepo.existsByName(requestDto.getName())) {
            throw  new HerbException(ErrorCodeEnum.EXISTED_USERNAME);
        }

        var GeneraEntity = GeneraMapper.requestToEntity(requestDto);
        GeneraEntity.setSlug(SlugGenerator.generateSlug(requestDto.getName()));
        generaRepo.save(GeneraEntity);
        return GeneraMapper.entityToResponse(GeneraEntity);
    }


    @Override
    public Boolean update(Long id, GeneraRequestDto requestDto) throws HerbException {
        var genera = generaRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));

        GeneraMapper.setValue(requestDto, genera);
        genera.setSlug(SlugGenerator.generateSlug(requestDto.getName()));
        generaRepo.save(genera);
        return true;
    }


    @Override
    public Page<GeneraResponseDto> search(SearchDto searchDto) {
        List<String> searchableFields = List.of("name", "slug");
        return super.search(searchDto, generaRepo, generaRepo, GeneraMapper::entityToResponse, searchableFields);
    }

    @Override
    public List<GeneraResponseDto> getAll() {
        var GeneraEntityList = generaRepo.findAll();
        return GeneraEntityList.stream().map(GeneraMapper::entityToResponse).toList();
    }


}

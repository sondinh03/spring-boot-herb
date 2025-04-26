package vnua.kltn.herb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.dto.request.DiseasesRequestDto;
import vnua.kltn.herb.dto.response.DiseasesResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.entity.Diseases;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.DiseasesRepository;
import vnua.kltn.herb.service.BaseSearchService;
import vnua.kltn.herb.service.DiseasesService;
import vnua.kltn.herb.service.mapper.DiseasesMapper;
import vnua.kltn.herb.utils.SlugGenerator;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DiseasesServiceImpl extends BaseSearchService<Diseases, DiseasesResponseDto> implements DiseasesService {
    private final DiseasesRepository diseasesRepo;
    private final DiseasesMapper diseasesMapper;

    @Override
    public DiseasesResponseDto getById(Long id) throws HerbException {
        var disease = diseasesRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        return diseasesMapper.entityToResponse(disease);
    }

    @Override
    public DiseasesResponseDto create(DiseasesRequestDto requestDto) throws HerbException {
        if (diseasesRepo.existsByName(requestDto.getName())) {
            throw  new HerbException(ErrorCodeEnum.EXISTED_USERNAME);
        }

        var diseasesEntity = diseasesMapper.requestToEntity(requestDto);
        diseasesEntity.setSlug(SlugGenerator.generateSlug(requestDto.getName()));
        diseasesRepo.save(diseasesEntity);
        return diseasesMapper.entityToResponse(diseasesEntity);
    }

    @Override
    public Boolean update(Long id, DiseasesRequestDto requestDto) throws HerbException {
        var disease = diseasesRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));

        diseasesMapper.setValue(requestDto, disease);
        diseasesRepo.save(disease);
        return true;
    }

    @Override
    public Page<DiseasesResponseDto> search(SearchDto searchDto) {
        List<String> searchableFields = List.of("name");
        return super.search(searchDto, diseasesRepo, diseasesRepo, diseasesMapper::entityToResponse, searchableFields);
    }

    @Override
    public List<DiseasesResponseDto> getAll() {
        var diseasesEntityList = diseasesRepo.findAll();
        return diseasesEntityList.stream().map(diseasesMapper::entityToResponse).toList();
    }
}

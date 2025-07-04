package vnua.kltn.herb.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.dto.request.DataSourceRequestDto;
import vnua.kltn.herb.dto.response.DataSourceResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.entity.DataSource;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.DataSourceRepository;
import vnua.kltn.herb.service.*;
import vnua.kltn.herb.service.mapper.DataSourceMapper;

import java.util.List;


@Service
@AllArgsConstructor
public class DataSourceServiceImpl extends BaseSearchService<DataSource, DataSourceResponseDto> implements DataSourceService {
    private final DataSourceRepository dataSourceRepo;
    private final DataSourceMapper dataSourceMapper;

    @Override
    public DataSourceResponseDto create(DataSourceRequestDto requestDto) throws HerbException {
        if (dataSourceRepo.existsByName(requestDto.getName())) {
            throw new HerbException(ErrorCodeEnum.DATA_SOURCE_EXISTS);
        }

        var dataSource = dataSourceMapper.requestToEntity(requestDto);
        dataSourceRepo.save(dataSource);
        return dataSourceMapper.entityToResponse(dataSource);
    }

    @Override
    public DataSourceResponseDto getById(Long id) throws HerbException {
        var dataSource = dataSourceRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        return dataSourceMapper.entityToResponse(dataSource);
    }

    @Override
    public Boolean update(Long id, DataSourceRequestDto requestDto) throws HerbException {
        var dataSource = dataSourceRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        dataSourceMapper.setValue(requestDto, dataSource);
        dataSourceRepo.save(dataSource);
        return true;
    }

    @Override
    public Boolean delete(Long id) throws HerbException {
        if (dataSourceRepo.existsById(id)) {
            dataSourceRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Page<DataSourceResponseDto> search(SearchDto dto) {
        dto.setPageSize(100);
        List<String> searchableFields = List.of("name", "author");
        return super.search(dto, dataSourceRepo, dataSourceRepo, dataSourceMapper::entityToResponse, searchableFields);
    }
}

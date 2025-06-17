package vnua.kltn.herb.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.dto.request.ArticleRequestDto;
import vnua.kltn.herb.dto.response.ArticleResponseDto;
import vnua.kltn.herb.dto.response.PlantResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.entity.Article;
import vnua.kltn.herb.entity.Plant;
import vnua.kltn.herb.entity.PlantMedia;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.ArticleRepository;
import vnua.kltn.herb.service.ArticleService;
import vnua.kltn.herb.service.BaseSearchService;
import vnua.kltn.herb.service.mapper.ArticleMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ArticleServiceImpl extends BaseSearchService<Article, ArticleResponseDto> implements ArticleService {
    private final ArticleRepository articleRepo;
    private final ArticleMapper articleMapper;

    @Override
    public ArticleResponseDto create(ArticleRequestDto requestDto) {
        var articleEntity = articleMapper.requestToEntity(requestDto);
        articleRepo.save(articleEntity);
        return articleMapper.entityToResponse(articleEntity);
    }

    @Override
    public ArticleResponseDto getById(Long id) throws HerbException {
        var article = articleRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        return articleMapper.entityToResponse(article);
    }

    @Override
    public Page<ArticleResponseDto> search(SearchDto searchDto) {
        List<String> searchableFields = List.of("id", "title", "excerpt", "content", "diseaseId", "authorId");
        return super.search(searchDto, articleRepo, articleRepo, articleMapper::entityToResponse, searchableFields);
    }

    @Override
    public Boolean update(Long id, ArticleRequestDto requestDto) throws HerbException {
        var articleEntity = articleRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        articleMapper.setValue(requestDto, articleEntity);
        articleRepo.save(articleEntity);
        return true;
    }
}

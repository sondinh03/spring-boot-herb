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
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.ArticleMediaRepository;
import vnua.kltn.herb.repository.ArticleRepository;
import vnua.kltn.herb.service.ArticleService;
import vnua.kltn.herb.service.BaseSearchService;
import vnua.kltn.herb.service.mapper.ArticleMapper;
import vnua.kltn.herb.utils.SlugGenerator;

import java.util.List;

@Service
@AllArgsConstructor
public class ArticleServiceImpl extends BaseSearchService<Article, ArticleResponseDto> implements ArticleService {
    private final ArticleRepository articleRepo;
    private final ArticleMapper articleMapper;
    private final ArticleMediaRepository articleMediaRepo;

    @Override
    public ArticleResponseDto create(ArticleRequestDto requestDto) {
        var articleEntity = articleMapper.requestToEntity(requestDto);
        articleEntity.setSlug(SlugGenerator.generateSlug(articleEntity.getTitle()));
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
        var articles = super.search(searchDto, articleRepo, articleRepo, articleMapper::entityToResponse, searchableFields);

        /*
        var articleIds = articles.getContent().stream().map(ArticleResponseDto::getId).toList();

        var featuredMedias = articleMediaRepo.findByIdArticleIdInAndIsFeaturedTrue(articleIds);

        Map<Long, ArticleMedia> mediaMap = featuredMedias.stream()
                .collect(Collectors.toMap(
                        am -> am.getId().getArticleId(),
                        am -> am,
                        (am1, am2) -> am1 // Giữ bản ghi đầu tiên nếu trùng
                ));

        return articles.map(article -> {
            var media = mediaMap.get(article.getId());
            if (media != null) {
                article.setFeaturedImage(media.getId().getMediaId());
            }
            return article;
        });
         */
        return articles;
    }

    @Override
    public Boolean update(Long id, ArticleRequestDto requestDto) throws HerbException {
        var articleEntity = articleRepo.findById(id).orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        articleMapper.setValue(requestDto, articleEntity);
        articleRepo.save(articleEntity);
        return true;
    }
}

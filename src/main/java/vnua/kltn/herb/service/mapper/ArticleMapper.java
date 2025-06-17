package vnua.kltn.herb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vnua.kltn.herb.dto.request.ArticleRequestDto;
import vnua.kltn.herb.dto.response.ArticleResponseDto;
import vnua.kltn.herb.entity.Article;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    ArticleResponseDto entityToResponse(Article article);

    Article requestToEntity(ArticleRequestDto articleRequestDto);

    void setValue(ArticleRequestDto requestDto, @MappingTarget Article article);
}

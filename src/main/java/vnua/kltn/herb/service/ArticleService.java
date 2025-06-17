package vnua.kltn.herb.service;

import org.springframework.data.domain.Page;
import vnua.kltn.herb.dto.request.ArticleRequestDto;
import vnua.kltn.herb.dto.response.ArticleResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;

public interface ArticleService {
    ArticleResponseDto create(ArticleRequestDto requestDto);

    ArticleResponseDto getById(Long id) throws HerbException;

    Page<ArticleResponseDto> search(SearchDto searchDto);

    Boolean update(Long id, ArticleRequestDto requestDto) throws HerbException;
}

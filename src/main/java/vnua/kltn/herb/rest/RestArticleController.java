package vnua.kltn.herb.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import vnua.kltn.herb.dto.request.ArticleRequestDto;
import vnua.kltn.herb.dto.response.ArticleResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.response.HerbResponse;
import vnua.kltn.herb.service.ArticleService;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class RestArticleController {
    private final ArticleService articleService;

    @PostMapping()
    public HerbResponse<ArticleResponseDto> create(@RequestBody @Valid ArticleRequestDto requestDto) {
        return new HerbResponse<>(articleService.create(requestDto));
    }

    @GetMapping("/{id}")
    public HerbResponse<ArticleResponseDto> getById(@PathVariable("id") Long id) throws HerbException {
        return new HerbResponse<>(articleService.getById(id));
    }

    @GetMapping("/search")
    HerbResponse<Page<ArticleResponseDto>> search(SearchDto dto) {
        return new HerbResponse<>(articleService.search(dto));
    }

    @PutMapping("/{id}")
    HerbResponse<Boolean> update(@PathVariable(value = "id") Long id, @RequestBody ArticleRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(articleService.update(id, requestDto));
    }

    @GetMapping("/total")
    HerbResponse<Long> getTotal() {
        return new HerbResponse<>(articleService.getTotal());
    }

}

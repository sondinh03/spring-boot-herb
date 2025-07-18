package vnua.kltn.herb.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vnua.kltn.herb.dto.request.ArticleRequestDto;
import vnua.kltn.herb.dto.request.ResearchRequestDto;
import vnua.kltn.herb.dto.response.ArticleResponseDto;
import vnua.kltn.herb.dto.response.ResearchResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.response.HerbResponse;
import vnua.kltn.herb.service.ArticleService;
import vnua.kltn.herb.service.ResearchService;

@RestController
@RequestMapping("/api/research")
@RequiredArgsConstructor
public class RestResearchController {
    private final ResearchService researchService;

    @PostMapping()
    public HerbResponse<ResearchResponseDto> create(@RequestBody @Valid ResearchRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(researchService.create(requestDto));
    }

    @PutMapping("/{id}")
    public HerbResponse<ResearchResponseDto> update(@PathVariable("id") Long id, @RequestBody @Valid ResearchRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(researchService.update(id, requestDto));
    }

    @GetMapping("/search")
    HerbResponse<Page<ResearchResponseDto>> search(SearchDto dto) {
        return new HerbResponse<>(researchService.search(dto));
    }

    @GetMapping("/{id}")
    HerbResponse<ResearchResponseDto> getById(@PathVariable(value = "id") Long id) throws HerbException {
        return new HerbResponse<>(researchService.getById(id));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/purchase/{id}")
    HerbResponse<Boolean> purchase(@PathVariable(value = "id") Long id) throws HerbException {
        return new HerbResponse<>(researchService.purchase(id));
    }
}

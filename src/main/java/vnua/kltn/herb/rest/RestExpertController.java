package vnua.kltn.herb.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import vnua.kltn.herb.dto.request.ExpertRequestDto;
import vnua.kltn.herb.dto.request.FamiliesRequestDto;
import vnua.kltn.herb.dto.response.ExpertResponseDto;
import vnua.kltn.herb.dto.response.FamiliesResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.response.HerbResponse;
import vnua.kltn.herb.service.ExpertService;
import vnua.kltn.herb.service.FamiliesService;

import java.util.List;

@RestController
@RequestMapping("/api/expert")
@RequiredArgsConstructor
@Slf4j
public class RestExpertController {
    private final ExpertService expertService;

    @PostMapping()
    HerbResponse<ExpertResponseDto> create(@RequestBody @Valid ExpertRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(expertService.create(requestDto));
    }

    @GetMapping("/{id}")
    public HerbResponse<ExpertResponseDto> getById(@PathVariable("id") Long id) throws HerbException {
        return new HerbResponse<>(expertService.getById(id));
    }

    @PutMapping("/{id}")
    public HerbResponse<Boolean> update(@PathVariable("id") Long id, @RequestBody ExpertRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(expertService.update(id, requestDto));
    }

    @GetMapping(value = "/search")
    HerbResponse<Page<ExpertResponseDto>> search(SearchDto dto) throws HerbException {
        return new HerbResponse<>(expertService.search(dto));
    }
}

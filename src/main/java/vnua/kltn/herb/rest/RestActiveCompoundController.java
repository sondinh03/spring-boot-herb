package vnua.kltn.herb.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import vnua.kltn.herb.dto.request.ActiveCompoundRequestDto;
import vnua.kltn.herb.dto.request.DiseasesRequestDto;
import vnua.kltn.herb.dto.response.ActiveCompoundResponseDto;
import vnua.kltn.herb.dto.response.DiseasesResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.response.HerbResponse;
import vnua.kltn.herb.service.ActiveCompoundService;
import vnua.kltn.herb.service.DiseasesService;

import java.util.List;

@RestController
@RequestMapping("/api/active-compound")
@RequiredArgsConstructor
@Slf4j
public class RestActiveCompoundController {
    private final ActiveCompoundService activeCompoundService;

    @PostMapping()
    public HerbResponse<ActiveCompoundResponseDto> create(@RequestBody @Valid ActiveCompoundRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(activeCompoundService.create(requestDto));
    }

    @GetMapping(value = "/{id}")
    public HerbResponse<ActiveCompoundResponseDto> getById(@PathVariable Long id) throws HerbException {
        return new HerbResponse<>(activeCompoundService.getById(id));
    }

    @GetMapping(value = "/search")
    public HerbResponse<Page<ActiveCompoundResponseDto>> search(SearchDto dto) {
        return new HerbResponse<>(activeCompoundService.search(dto));
    }

    @PutMapping(value = "/{id}")
    public HerbResponse<Boolean> update(@PathVariable("id") Long id, @RequestBody @Valid ActiveCompoundRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(activeCompoundService.update(id, requestDto));
    }
}

package vnua.kltn.herb.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import vnua.kltn.herb.dto.request.FamiliesRequestDto;
import vnua.kltn.herb.dto.response.FamiliesResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.response.HerbResponse;
import vnua.kltn.herb.service.FamiliesService;

import java.util.List;

@RestController
@RequestMapping("/api/families")
@RequiredArgsConstructor
@Slf4j
public class RestFamiliesController {
    private final FamiliesService familiesService;

    @PostMapping()
    HerbResponse<FamiliesResponseDto> create(@RequestBody @Valid FamiliesRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(familiesService.create(requestDto));
    }

    @GetMapping("/{id}")
    public HerbResponse<FamiliesResponseDto> getById(@PathVariable("id") Long id) throws HerbException {
        return new HerbResponse<>(familiesService.getById(id));
    }

    @GetMapping(value = "/search")
    HerbResponse<Page<FamiliesResponseDto>> search(SearchDto dto) {
        return new HerbResponse<>(familiesService.search(dto));
    }

    @PutMapping("/{id}")
    HerbResponse<Boolean> update(@PathVariable(value = "id") Long id, @RequestBody FamiliesRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(familiesService.update(id, requestDto));
    }

    @GetMapping(value = "/get-all")
    HerbResponse<List<FamiliesResponseDto>> getAll() {
        return new HerbResponse<>(familiesService.getAll());
    }
}

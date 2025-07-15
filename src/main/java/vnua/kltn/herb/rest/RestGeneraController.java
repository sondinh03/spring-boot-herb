package vnua.kltn.herb.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import vnua.kltn.herb.dto.request.GeneraRequestDto;
import vnua.kltn.herb.dto.response.GeneraResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.response.HerbResponse;
import vnua.kltn.herb.service.GeneraService;

import java.util.List;

@RestController
@RequestMapping("/api/genera")
@RequiredArgsConstructor
@Slf4j
public class RestGeneraController {
    private final GeneraService generaService;

    @PostMapping()
    HerbResponse<GeneraResponseDto> create(@RequestBody @Valid GeneraRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(generaService.create(requestDto));
    }

    @GetMapping("/{id}")
    public HerbResponse<GeneraResponseDto> getById(@PathVariable("id") Long id) throws HerbException {
        return new HerbResponse<>(generaService.getById(id));
    }

    @GetMapping(value = "/search")
    HerbResponse<Page<GeneraResponseDto>> search(SearchDto dto) {
        return new HerbResponse<>(generaService.search(dto));
    }

    @PutMapping("/{id}")
    HerbResponse<Boolean> update(@PathVariable(value = "id") Long id, @RequestBody GeneraRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(generaService.update(id, requestDto));
    }

    @GetMapping(value = "/get-all")
    HerbResponse<List<GeneraResponseDto>> getAll() {
        return new HerbResponse<>(generaService.getAll());
    }
}

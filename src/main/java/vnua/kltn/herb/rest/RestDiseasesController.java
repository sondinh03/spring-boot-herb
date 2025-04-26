package vnua.kltn.herb.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vnua.kltn.herb.dto.request.MediaRequestDto;
import vnua.kltn.herb.dto.request.DiseasesRequestDto;
import vnua.kltn.herb.dto.response.DiseasesResponseDto;
import vnua.kltn.herb.dto.response.MediaResponseDto;
import vnua.kltn.herb.dto.response.DiseasesResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.response.HerbResponse;
import vnua.kltn.herb.service.DiseasesService;
import vnua.kltn.herb.service.MediaService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/diseases")
@RequiredArgsConstructor
@Slf4j
public class RestDiseasesController {
    private final DiseasesService diseasesService;

    @PostMapping()
    HerbResponse<DiseasesResponseDto> create(@RequestBody @Valid DiseasesRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(diseasesService.create(requestDto));
    }

    @GetMapping("/{id}")
    public HerbResponse<DiseasesResponseDto> getById(@PathVariable("id") Long id) throws HerbException {
        return new HerbResponse<>(diseasesService.getById(id));
    }

    @GetMapping(value = "/search")
    HerbResponse<Page<DiseasesResponseDto>> search(SearchDto dto) {
        return new HerbResponse<>(diseasesService.search(dto));
    }

    @PutMapping("/{id}")
    HerbResponse<Boolean> update(@PathVariable(value = "id") Long id, @RequestBody DiseasesRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(diseasesService.update(id, requestDto));
    }

    @GetMapping(value = "/get-all")
    HerbResponse<List<DiseasesResponseDto>> getAll() {
        return new HerbResponse<>(diseasesService.getAll());
    }
}

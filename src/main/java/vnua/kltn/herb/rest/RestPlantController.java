package vnua.kltn.herb.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import vnua.kltn.herb.dto.request.PlantRequestDto;
import vnua.kltn.herb.dto.response.PlantResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.response.HerbResponse;
import vnua.kltn.herb.service.PlantService;

@RestController
@RequestMapping("/api/plants")
@RequiredArgsConstructor
public class RestPlantController {
    private final PlantService plantService;

    @PostMapping()
    public HerbResponse<PlantResponseDto> create(@RequestBody PlantRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(plantService.create(requestDto));
    }

    @GetMapping("/{id}")
    public HerbResponse<PlantResponseDto> getById(@PathVariable("id") Long id) throws HerbException {
        return new HerbResponse<>(plantService.getById(id));
    }

    @PutMapping("/{id}")
    public HerbResponse<Boolean> update(@PathVariable("id") Long id, @RequestBody PlantRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(plantService.update(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public HerbResponse<Boolean> delete(@PathVariable("id") Long id) throws HerbException {
        return new HerbResponse<>(plantService.delete(id));
    }

    @GetMapping("/search")
    public HerbResponse<Page<PlantResponseDto>> search(SearchDto dto) {
        return new HerbResponse<>(plantService.search(dto));
    }

    @GetMapping("/search/first-letter")
    public HerbResponse<Page<PlantResponseDto>> findByNameWithFirstLetter(SearchDto dto) {
        return new HerbResponse<>(plantService.findByNameWithFirstLetter(dto));
    }
}

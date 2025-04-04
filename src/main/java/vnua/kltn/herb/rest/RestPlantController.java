package vnua.kltn.herb.rest;

import jakarta.validation.Valid;
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
    HerbResponse<PlantResponseDto> create(@RequestBody @Valid PlantRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(plantService.create(requestDto));
    }

    @GetMapping(value = "/search")
    HerbResponse<Page<PlantResponseDto>> search(SearchDto dto) {
        return new HerbResponse<>(plantService.search(dto));
    }

}

package vnua.kltn.herb.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vnua.kltn.herb.dto.request.PlantFamilyRequestDto;
import vnua.kltn.herb.dto.response.PlantFamilyResponseDto;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.response.HerbResponse;
import vnua.kltn.herb.service.PlantFamilyService;

import java.util.List;

@RestController
@RequestMapping("/api/plant-family")
@RequiredArgsConstructor
public class RestPlantFamilyController {
    private final PlantFamilyService plantFamilyService;

    @PostMapping()
    public HerbResponse<PlantFamilyResponseDto> create(@RequestBody  PlantFamilyRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(plantFamilyService.create(requestDto));
    }

    @GetMapping("/{id}")
    public HerbResponse<PlantFamilyResponseDto> getById(@PathVariable("id") Long id) throws HerbException {
        return new HerbResponse<>(plantFamilyService.getById(id));
    }

    @PutMapping("/{id}")
    public HerbResponse<Boolean> update(@PathVariable("id") Long id, @RequestBody PlantFamilyRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(plantFamilyService.update(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public HerbResponse<Boolean> delete(@PathVariable("id") Long id) throws HerbException {
        return new HerbResponse<>(plantFamilyService.delete(id));
    }

        @GetMapping("/search")
        public HerbResponse<List<PlantFamilyResponseDto>> search(@RequestParam(value = "text", required = false) String text) {
            return new HerbResponse<>(plantFamilyService.search(text));
        }
}

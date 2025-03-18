package vnua.kltn.herb.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vnua.kltn.herb.dto.request.DiseaseGroupRequestDto;
import vnua.kltn.herb.dto.response.DiseaseGroupResponseDto;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.response.HerbResponse;
import vnua.kltn.herb.service.DiseaseGroupService;

import java.util.List;

@RestController
@RequestMapping("/api/disease-group")
@RequiredArgsConstructor
public class RestDiseaseGroupController {
    private final DiseaseGroupService diseaseGroupService;

    @PostMapping()
    public HerbResponse<DiseaseGroupResponseDto> create(@RequestBody  DiseaseGroupRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(diseaseGroupService.create(requestDto));
    }

    @GetMapping("/{id}")
    public HerbResponse<DiseaseGroupResponseDto> getById(@PathVariable("id") Long id) throws HerbException {
        return new HerbResponse<>(diseaseGroupService.getById(id));
    }

    @PutMapping("/{id}")
    public HerbResponse<Boolean> update(@PathVariable("id") Long id, @RequestBody DiseaseGroupRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(diseaseGroupService.update(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public HerbResponse<Boolean> delete(@PathVariable("id") Long id) throws HerbException {
        return new HerbResponse<>(diseaseGroupService.delete(id));
    }

    @GetMapping("/search")
    public HerbResponse<List<DiseaseGroupResponseDto>> search(@RequestParam(value = "text", required = false) String text) {
        return new HerbResponse<>(diseaseGroupService.search(text));
    }
}

package vnua.kltn.herb.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vnua.kltn.herb.constant.enums.FavoritableTypeEnum;
import vnua.kltn.herb.dto.request.FavoriteRequestDto;
import vnua.kltn.herb.dto.request.MediaRequestDto;
import vnua.kltn.herb.dto.request.PlantRequestDto;
import vnua.kltn.herb.dto.response.FavoriteResponseDto;
import vnua.kltn.herb.dto.response.MediaResponseDto;
import vnua.kltn.herb.dto.response.PlantResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.response.HerbResponse;
import vnua.kltn.herb.service.FavoriteService;
import vnua.kltn.herb.service.PlantMediaService;
import vnua.kltn.herb.service.PlantService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/plants")
@RequiredArgsConstructor
public class RestPlantController {
    private final PlantService plantService;
    private final PlantMediaService plantMediaService;
    private final FavoriteService favoriteService;

    @PostMapping()
    HerbResponse<PlantResponseDto> create(@RequestBody @Valid PlantRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(plantService.create(requestDto));
    }

    @GetMapping("/{id}")
    HerbResponse<PlantResponseDto> getById(@PathVariable(value = "id") Long id) throws HerbException {
        return new HerbResponse<>(plantService.getById(id));
    }

    @GetMapping(value = "/search")
    HerbResponse<Page<PlantResponseDto>> search(SearchDto dto) {
        return new HerbResponse<>(plantService.search(dto));
    }

    @PutMapping("/{id}")
    HerbResponse<Boolean> update(@PathVariable(value = "id") Long id, @RequestBody PlantRequestDto requestDto) throws HerbException {
        return new HerbResponse<>(plantService.update(id, requestDto));
    }

    @GetMapping("/{plantId}/media-ids")
    public HerbResponse<List<Long>> getMediaIds(@PathVariable(value = "plantId") Long plantId) {
        return new HerbResponse<>(plantMediaService.findMediaIdsByPlantId(plantId));
    }

    @GetMapping("/{plantId}/medias")
    public HerbResponse<List<MediaResponseDto>> getMedias(@PathVariable(value = "plantId") Long plantId) throws HerbException {
        return new HerbResponse<>(plantMediaService.findMediaByPlantId(plantId));
    }

    @GetMapping("/{plantId}/media-featured")
    public HerbResponse<Long> getMediaIdFeatured(@PathVariable(value = "plantId") Long plantId) {
        return new HerbResponse<>(plantMediaService.findMediaIdFeaturedByPlantId(plantId));
    }

    @PostMapping("/{plantId}/media/upload")
    public HerbResponse<Boolean> uploadMedia(@PathVariable(value = "plantId") Long plantId, @ModelAttribute MediaRequestDto requestDto) throws HerbException, IOException {
        return new HerbResponse<>(plantService.uploadMedia(plantId, requestDto));
    }

    @PostMapping("/{plantId}/favorite")
    @PreAuthorize("hasRole('USER')")
    public HerbResponse<FavoriteResponseDto> favorite(@PathVariable(value = "plantId") Long plantId) throws HerbException, IOException {
        var requestDto = new FavoriteRequestDto();
        requestDto.setFavoritableType(FavoritableTypeEnum.PLANT.getType());
        requestDto.setFavoritableId(plantId);
        return new HerbResponse<>(favoriteService.toggleFavorite(requestDto));
    }
}

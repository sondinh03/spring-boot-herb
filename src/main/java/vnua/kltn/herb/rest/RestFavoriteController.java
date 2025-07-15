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
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class RestFavoriteController {
    private final PlantMediaService plantMediaService;
    private final FavoriteService favoriteService;

    @PostMapping("/plants/{id}")
    @PreAuthorize("hasRole('USER')")
    public HerbResponse<FavoriteResponseDto> favoritePlant(@PathVariable(value = "id") Long id) throws HerbException {
        var requestDto = new FavoriteRequestDto();
        requestDto.setFavoritableType(FavoritableTypeEnum.PLANT.getType());
        requestDto.setFavoritableId(id);
        return new HerbResponse<>(favoriteService.toggleFavorite(requestDto));
    }

    @PostMapping("/articles/{id}")
    @PreAuthorize("hasRole('USER')")
    public HerbResponse<FavoriteResponseDto> favoriteArticle(@PathVariable(value = "id") Long id) throws HerbException {
        var requestDto = new FavoriteRequestDto();
        requestDto.setFavoritableType(FavoritableTypeEnum.ARTICLE.getType());
        requestDto.setFavoritableId(id);
        return new HerbResponse<>(favoriteService.toggleFavorite(requestDto));
    }
}

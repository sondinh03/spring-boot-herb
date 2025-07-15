package vnua.kltn.herb.service;

import org.springframework.data.domain.Page;
import vnua.kltn.herb.dto.request.FamiliesRequestDto;
import vnua.kltn.herb.dto.request.FavoriteRequestDto;
import vnua.kltn.herb.dto.response.FamiliesResponseDto;
import vnua.kltn.herb.dto.response.FavoriteResponseDto;
import vnua.kltn.herb.dto.search.SearchDto;
import vnua.kltn.herb.exception.HerbException;

import java.util.List;

public interface FavoriteService {
    FavoriteResponseDto toggleFavorite(FavoriteRequestDto favoriteRequestDto) throws HerbException;
}

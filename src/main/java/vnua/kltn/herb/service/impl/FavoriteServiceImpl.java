package vnua.kltn.herb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnua.kltn.herb.constant.enums.ErrorCodeEnum;
import vnua.kltn.herb.constant.enums.FavoritableTypeEnum;
import vnua.kltn.herb.dto.request.FavoriteRequestDto;
import vnua.kltn.herb.dto.response.FavoriteResponseDto;
import vnua.kltn.herb.entity.Article;
import vnua.kltn.herb.entity.Favorite;
import vnua.kltn.herb.entity.Plant;
import vnua.kltn.herb.exception.HerbException;
import vnua.kltn.herb.repository.ArticleRepository;
import vnua.kltn.herb.repository.FavoriteRepository;
import vnua.kltn.herb.repository.PlantRepository;
import vnua.kltn.herb.service.BaseSearchService;
import vnua.kltn.herb.service.FavoriteService;
import vnua.kltn.herb.service.UserService;
import vnua.kltn.herb.service.mapper.FavoriteMapper;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl extends BaseSearchService<Favorite, FavoriteResponseDto> implements FavoriteService {
    private final FavoriteRepository favoriteRepo;
    private final PlantRepository plantRepo;
    private final ArticleRepository articleRepo;
    private final FavoriteMapper favoriteMapper;
    private final UserService userService;

    @Override
    @Transactional
    public FavoriteResponseDto toggleFavorite(FavoriteRequestDto requestDto) throws HerbException {
        var userId = userService.getCurrentUser().getId();
        requestDto.setUserId(userId);
        var favoritableId = requestDto.getFavoritableId();
        var favoritableType = requestDto.getFavoritableType();
        Favorite favorite;

        // Kiểm tra favoritableType hợp lệ
        if (!FavoritableTypeEnum.PLANT.getType().equals(favoritableType) &&
                !FavoritableTypeEnum.ARTICLE.getType().equals(favoritableType)) {
            throw new HerbException(ErrorCodeEnum.INVALID_FAVORITABLE_TYPE);
        }

        // Kiểm tra sự tồn tại của plant hoặc article
        if (FavoritableTypeEnum.PLANT.getType().equals(favoritableType)) {
            plantRepo.findById(favoritableId)
                    .orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        } else {
            articleRepo.findById(favoritableId)
                    .orElseThrow(() -> new HerbException(ErrorCodeEnum.NOT_FOUND));
        }

        var existingFavorite = favoriteRepo.findByUserIdAndFavoritableTypeAndFavoritableId(userId, favoritableType, favoritableId);

        Integer favoriteCount;
        boolean isFavorited;

        if (existingFavorite.isPresent()) {
            favorite = existingFavorite.get();
            isFavorited = !favorite.getIsActive();
            favorite.setIsActive(isFavorited);
            favoriteRepo.save(favorite);

            if (FavoritableTypeEnum.PLANT.getType().equals(favoritableType)) {
                if (isFavorited) {
                    plantRepo.incrementFavoritesCount(favoritableId);
                } else {
                    plantRepo.decrementFavoritesCount(favoritableId);
                }
                favoriteCount = plantRepo.findById(favoritableId)
                        .map(Plant::getFavoritesCount)
                        .orElse(0);
            } else {
                if (isFavorited) {
                    articleRepo.incrementFavoritesCount(favoritableId);
                } else {
                    articleRepo.decrementFavoritesCount(favoritableId);
                }
                favoriteCount = articleRepo.findById(favoritableId)
                        .map(Article::getFavoritesCount)
                        .orElse(0);
            }
        } else {
            favorite = favoriteMapper.requestToEntity(requestDto);
            if (favorite.getFavoritableType() == null) {
                throw new HerbException(ErrorCodeEnum.INVALID_FAVORITABLE_TYPE);
            }

            favorite.setIsActive(true);
            favoriteRepo.save(favorite);

            // Cập nhật số lượt thích
            if (FavoritableTypeEnum.PLANT.getType().equals(favoritableType)) {
                plantRepo.incrementFavoritesCount(favoritableId);
                favoriteCount = plantRepo.findById(favoritableId)
                        .map(Plant::getFavoritesCount)
                        .orElse(0);
            } else {
                articleRepo.incrementFavoritesCount(favoritableId);
                favoriteCount = articleRepo.findById(favoritableId)
                        .map(Article::getFavoritesCount)
                        .orElse(0);
            }
        }

        return favoriteMapper.entityToResponse(favorite);
    }
}

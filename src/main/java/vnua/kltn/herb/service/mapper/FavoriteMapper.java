package vnua.kltn.herb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vnua.kltn.herb.dto.request.FavoriteRequestDto;
import vnua.kltn.herb.dto.response.FavoriteResponseDto;
import vnua.kltn.herb.entity.Favorite;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {
    FavoriteResponseDto entityToResponse(Favorite entity);

    Favorite requestToEntity(FavoriteRequestDto request);

    void setValue(FavoriteRequestDto requestDto, @MappingTarget Favorite entity);
}

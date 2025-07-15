package vnua.kltn.herb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vnua.kltn.herb.dto.BaseObjectDto;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteResponseDto extends BaseObjectDto {
    private Long id;
    private Long userId;
    private Integer favoritableType;
    private Long favoritableId;
    private Boolean isActive;
    private Integer favoriteCount;
}

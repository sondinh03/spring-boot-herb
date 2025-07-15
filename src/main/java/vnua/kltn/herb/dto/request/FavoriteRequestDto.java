package vnua.kltn.herb.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vnua.kltn.herb.dto.BaseObjectDto;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteRequestDto extends BaseObjectDto {
    private Long userId;
    private Integer favoritableType;
    private Long favoritableId;
    private Boolean isActive;
}

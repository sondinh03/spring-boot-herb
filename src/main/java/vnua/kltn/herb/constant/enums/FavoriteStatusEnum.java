package vnua.kltn.herb.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FavoriteStatusEnum {
    ACTIVE(1, "Thích"),
    INACTIVE(2, "Không thích"),
    ;
    
    private final Integer type;
    private final String value;
}

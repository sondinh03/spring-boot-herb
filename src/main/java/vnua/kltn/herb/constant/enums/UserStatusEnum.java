package vnua.kltn.herb.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatusEnum {
    ACTIVE(1, "Active"),
    INACTIVE(2, "Inactive"),
    BLOCKED(3, "Blocked"),
    ;
    
    private final Integer type;
    private final String value;
}

package vnua.kltn.herb.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatusEnum {
    ACTIVE(1, "Hoạt động"),
    INACTIVE(2, "Không hoạt động"),
    BLOCKED(3, "Đã chặn"),
    ;
    
    private final Integer type;
    private final String value;
}

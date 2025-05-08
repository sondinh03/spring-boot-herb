package vnua.kltn.herb.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatusEnum {
    ACTIVE(1, "Hoạt động"),
    INACTIVE(2, "Tạm dừng hoạt động"),
    BLOCKED(3, "Bị chặn"),
    PENDING(4, "Chờ duyệt"),
    DELETED(5, "Đã xóa"),
    ;
    
    private final Integer type;
    private final String value;
}

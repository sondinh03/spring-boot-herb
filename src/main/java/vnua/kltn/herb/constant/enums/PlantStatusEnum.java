package vnua.kltn.herb.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlantStatusEnum {
    DRAFT(1, "Bản nháp"),
    PUBLISHED(2, "Đã xuất bản"),
    PENDING(3, "Đã lưu trữ"),
    ;
    
    private final Integer type;
    private final String value;
}

package vnua.kltn.herb.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileTypeEnum {
    IMAGE(1, "image/"),
    VIDEO(2, "video/"),
    DOCUMENT(3, "document/"),
    ;

    private final Integer type;
    private final String value;
}

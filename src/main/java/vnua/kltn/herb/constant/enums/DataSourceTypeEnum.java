package vnua.kltn.herb.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataSourceTypeEnum {
    BOOK(1, "Sách"),
    JOURNAL(2, "Tạp chí"),
    WEBSITE(3, "Website");

    private final Integer type;
    private final String description;

    public static DataSourceTypeEnum fromType(Integer type) {
        if (type == null) {
            return null;
        }
        for (DataSourceTypeEnum dataSourceType : DataSourceTypeEnum.values()) {
            if (dataSourceType.getType().equals(type)) {
                return dataSourceType;
            }
        }
        return null;
    }
}

package vnua.kltn.herb.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FavoritableTypeEnum {
    PLANT(1, "Cây dược liệu"),
    ARTICLE(2, "Bài viết"),
    RESEARCH(3, "Nghiên cứu khoa học");

    private final Integer type;
    private final String description;

    public static FavoritableTypeEnum fromType(Integer type) {
        if (type == null) {
            return null;
        }
        for (FavoritableTypeEnum dataSourceType : FavoritableTypeEnum.values()) {
            if (dataSourceType.getType().equals(type)) {
                return dataSourceType;
            }
        }
        return null;
    }
}

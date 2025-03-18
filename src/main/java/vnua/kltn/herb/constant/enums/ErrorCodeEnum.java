package vnua.kltn.herb.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {
    SUCCESS(200, "Success"),
    NOT_FOUND(404, "Not Found"),
    PLAN_FAMILY_EXIST(400_001, "Họ thực vật đã tồn tại."),
    PLAN_IS_EXIST(400_002, "Cây dược liệu đã có trong cơ sở dữ liệu."),
    FILE_NOT_EMPTY(400_003, "File không được trống."),
    FILE_UPLOAD_ERROR(400_004, "File không thể lưu.")

    ;

    private final int errorCode;
    private final String message;
}

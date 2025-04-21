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
    FILE_UPLOAD_ERROR(400_004, "File không thể lưu."),
    EXISTED_USERNAME(400_005, "Tên đăng nhập đã tồn tại"),
    EXISTED_EMAIL(400_406, "Địa chỉ email đã được sử dụng"),
    CONFIRM_PASSWORD_ERROR(400_407, "Xác nhận mật khẩu và ật khảu chủa khơớp. "),
    UNAUTHORIZED(401, "Unauthorized"),
    EXISTED_NAME(400_008, "Tên dược liệu đã tồn tại"),
    INVALID_TOKEN(401_002, "Token ko hop le"),
    NOT_SUPPORT(401_005, "Định dạng file không được hỗ trợ"),
    ;

    private final int errorCode;
    private final String message;
}

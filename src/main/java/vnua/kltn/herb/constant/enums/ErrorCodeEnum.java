package vnua.kltn.herb.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {
    // Successful response
    SUCCESS(200, "Success"),

    // Standard HTTP errors
    UNAUTHORIZED(401, "Unauthorized"),
    NOT_FOUND(404, "Not Found"),

    // Authentication/Authorization errors (401xxx)
    INVALID_TOKEN(401001, "Token không hợp lệ"),

    // Bad Request errors (400xxx)
    // -- User management errors (400100-400199)
    EXISTED_USERNAME(400101, "Tên đăng nhập đã tồn tại"),
    EXISTED_EMAIL(400102, "Địa chỉ email đã được sử dụng"),
    CONFIRM_PASSWORD_ERROR(400103, "Xác nhận mật khẩu và mật khẩu chưa khớp"),

    // -- Plant management errors (400200-400299)
    PLANT_FAMILY_EXIST(400201, "Họ thực vật đã tồn tại"),
    PLANT_IS_EXIST(400202, "Cây dược liệu đã có trong cơ sở dữ liệu"),
    EXISTED_NAME(400203, "Tên đã tồn tại"),

    // -- File management errors (400300-400399)
    FILE_NOT_EMPTY(400301, "File không được trống"),
    FILE_UPLOAD_ERROR(400302, "File không thể lưu"),
    NOT_SUPPORT(400303, "Định dạng file không được hỗ trợ");

    private final int errorCode;
    private final String message;
}

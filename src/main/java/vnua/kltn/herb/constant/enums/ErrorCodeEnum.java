package vnua.kltn.herb.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum định nghĩa các mã lỗi và thông điệp tương ứng cho hệ thống.
 * Mã lỗi được tổ chức theo nhóm:
 * - 200: Thành công
 * - 400xxx: Lỗi Bad Request (client)
 * - 401xxx: Lỗi xác thực
 * - 403: Lỗi truy cập bị cấm
 * - 404: Lỗi không tìm thấy tài nguyên
 * - 500: Lỗi hệ thống
 */

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {
    // Thành công
    SUCCESS(200, "Thành công"),

    // Lỗi HTTP chuẩn
    BAD_REQUEST(400000, "Yêu cầu không hợp lệ"),
    INVALID_REQUEST(400001, "Yêu cầu không hợp lệ hoặc thiếu dữ liệu"),
    UNAUTHORIZED(401, "Không được phép truy cập"),
    FORBIDDEN(403, "Truy cập bị cấm"),
    NOT_FOUND(404, "Không tìm thấy tài nguyên"),
    INTERNAL_SERVER_ERROR(500, "Lỗi hệ thống"),

    // Lỗi xác thực (401xxx)
    INVALID_TOKEN(401001, "Token không hợp lệ"),
    EXPIRED_TOKEN(401002, "Token đã hết hạn"),
    MISSING_REFRESH_TOKEN(401003, "Thiếu refresh token"),
    TOKEN_BLACKLISTED(401004, "Token đã bị thu hồi"),
    TOKEN_GENERATION_ERROR(401005, "Lỗi tạo token mới"),

    // Lỗi quản lý người dùng (4001xx)
    EXISTED_USERNAME(400101, "Tên đăng nhập đã tồn tại"),
    EXISTED_EMAIL(400102, "Email đã được sử dụng"),
    CONFIRM_PASSWORD_MISMATCH(400103, "Mật khẩu xác nhận không khớp"),

    // Lỗi quản lý cây dược liệu (4002xx)
    PLANT_FAMILY_EXISTS(400201, "Họ thực vật đã tồn tại"),
    PLANT_EXISTS(400202, "Cây dược liệu đã tồn tại"),
    EXISTED_PLANT_NAME(400203, "Tên cây đã tồn tại"),
    INVALID_ENTITY_TYPE(400204, "Loại thực thể không hợp lệ"),

    // Lỗi quản lý bệnh (4003xx)
    DISEASE_EXISTS(400301, "Loại bệnh đã tồn tại"),

    // Lỗi quản lý bài viết (4004xx)
    TITLE_EXISTS(400401, "Tiêu đề đã tồn tại"),
    TITLE_REQUIRED(400402, "Tiêu đề là bắt buộc"),
    TITLE_TOO_LONG(400403, "Tiêu đề quá dài"),

    RESEARCH_NOT_FOUND(400404, "Không tìm thấy nghiên cứu khoa học"),
    MEDIA_NOT_FOUND(400404, "Không tìm thấy tệp"),

    // Lỗi quản lý nguồn dữ liệu (4005xx)
    DATA_SOURCE_EXISTS(400501, "Sách này đã tồn tại"),

    // Lỗi quản lý nguồn dữ liệu (4005xx)
    NAME_EXISTS(400501, "Tên này đã tồn tại"),

    // Lỗi quản lý hoạt chất và yêu thích (4006xx)
    COMPOUND_EXISTS(400601, "Hoạt chất đã tồn tại"),
    INVALID_FAVORITABLE_TYPE(400602, "Loại dữ liệu yêu thích không hợp lệ"),

    // Lỗi quản lý tệp (4007xx)
    EMPTY_FILE(400701, "Tệp không được để trống"),
    FILE_UPLOAD_FAILED(400702, "Không thể lưu tệp"),
    UNSUPPORTED_FILE_FORMAT(400703, "Định dạng tệp không được hỗ trợ"),
    INVALID_FILE(400704, "Tệp không hợp lệ"),
    FILE_IO_ERROR(400705, "Lỗi khi xử lý tệp"),
    FILE_DATABASE_ERROR(400706, "Lỗi khi lưu thông tin tệp vào cơ sở dữ liệu"),
    FILE_TOO_LARGE(400707, "Tệp vượt quá kích thước cho phép");

    private final int errorCode;
    private final String message;
}

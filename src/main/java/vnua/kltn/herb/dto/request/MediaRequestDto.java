package vnua.kltn.herb.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaRequestDto {
    // File được upload (chỉ dùng khi upload mới)
    private MultipartFile file;

    // Mô tả thay thế cho media
    @Size(max = 255, message = "Alt text không được vượt quá 255 ký tự")
    private String altText;

    // Loại file (có thể được xác định tự động từ file hoặc được chỉ định thủ công)
    // 1 = image, 2 = video, 3 = document, 4 = audio, 0 = other
    private Integer fileType;

    // ID của đối tượng liên quan (nếu media được liên kết với một đối tượng cụ thể)
    private Long relatedEntityId;

    // Loại đối tượng liên quan (plant, article, expert, etc.)
    private String relatedEntityType;

    // Cờ đánh dấu media là featured cho đối tượng liên quan
    private Boolean isFeatured;

    // Vị trí sắp xếp của media trong danh sách (nếu có nhiều media cho một đối tượng)
    private Integer sortOrder;

    // Thẻ tags cho media (để dễ dàng tìm kiếm)
    private String tags;

    // Nhóm media (để phân loại media theo mục đích sử dụng)
    private String mediaGroup;

    // Cờ đánh dấu media là public hoặc private
    private Boolean isPublic;

    // Thông tin bản quyền
    @Size(max = 500, message = "Thông tin bản quyền không được vượt quá 500 ký tự")
    private String copyright;

    // Ngày hết hạn (nếu media chỉ có hiệu lực trong một khoảng thời gian)
    private String expiryDate;
    // Metadata bổ sung (có thể lưu dưới dạng JSON)
    private String metadata;
}

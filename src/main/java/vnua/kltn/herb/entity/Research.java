package vnua.kltn.herb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "research")
public class Research extends BaseEntity {
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title; // Tiêu đề của nghiên cứu

    @Column(nullable = false, unique = true)
    private String slug; // Chuỗi định danh duy nhất

    @Column(name = "abstract", columnDefinition = "TEXT")
    private String abstractText; // Tóm tắt nội dung của nghiên cứu

    @NotBlank(message = "Content is required")
    @Size(max = 5000, message = "Content must not exceed 5000 characters")
    private String content; // Nội dung chi tiết của nghiên cứu

    @Column(columnDefinition = "TEXT")
    private String authors; // Danh sách tác giả của nghiên cứu (có thể lưu dưới dạng chuỗi)

    private String institution; // Tên tổ chức hoặc cơ quan thực hiện nghiên cứu

    @Column(name = "published_year")
    private Integer publishedYear; // Năm xuất bản của nghiên cứu

    private String journal; // Tên tạp chí hoặc nơi nghiên cứu được công bố

    private String field; // Lĩnh vực nghiên cứu (VD: "Y học", "Thực vật học")

    @Column(nullable = false)
    private Integer status = 1; // Trạng thái của nghiên cứu, mặc định là 1 (DRAFT - bản nháp)

    private Integer views = 0; // Số lượt xem nghiên cứu, mặc định là 0

    @Column(name = "media_id")
    private Long mediaId; // Id của file

    @Column(name = "download_price")
    private BigDecimal downloadPrice; // Phí tải xuống

    @Min(value = 1, message = "Preview pages must be at least 1")
    @Max(value = 100, message = "Preview pages must not exceed 100")
    @Column(name = "preview_pages")
    private Integer previewPages;
}

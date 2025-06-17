package vnua.kltn.herb.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vnua.kltn.herb.entity.Diseases;
import vnua.kltn.herb.entity.Media;
import vnua.kltn.herb.entity.User;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseDto extends BaseResponseDto {
    private Long id;
    private String title;
    private String slug;
    private String excerpt;
    private String content;
    private Long featuredImage;
    private Integer status = 1; // Default: DRAFT
    private Boolean isFeatured = false;
    private Boolean allowComments = true;
    private Long views;
    private Long authorId;
    private Long diseaseId;
    private LocalDateTime publishedAt;
}

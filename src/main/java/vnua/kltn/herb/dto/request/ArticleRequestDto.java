package vnua.kltn.herb.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vnua.kltn.herb.dto.response.BaseResponseDto;
import vnua.kltn.herb.entity.Media;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequestDto {
    private String title;
    private String slug;
    private String excerpt;
    private String content;
    private Long featuredImage;
    private Integer status = 1;
    private Boolean isFeatured = false;
    private Boolean allowComments = true;
    private Long views;
    private Long authorId;
    private Long diseaseId;
    private LocalDateTime publishedAt;
}

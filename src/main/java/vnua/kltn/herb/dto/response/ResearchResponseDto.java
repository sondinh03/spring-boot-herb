package vnua.kltn.herb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResearchResponseDto extends BaseResponseDto {
    private Long id;
    private String title;
    private String slug;
    private String abstractText;
    private String content;
    private String authors;
    private String institution;
    private Integer publishedYear;
    private String journal;
    private String field;
    private Integer status;
    private Integer views;
    private Long mediaId;
    private BigDecimal downloadPrice;
    private String mediaUrl;
    private Integer previewPages;
    private Boolean isPurchased;
}

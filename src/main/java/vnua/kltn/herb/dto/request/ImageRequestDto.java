package vnua.kltn.herb.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageRequestDto {
    private String caption;
    private String altText;
    private Boolean isPrimary;
    private Integer displayOrder;
}

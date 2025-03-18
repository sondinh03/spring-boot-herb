package vnua.kltn.herb.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileRequestDto {
    private String name;
    private MultipartFile file;
    private String originalName;
    private String description;
    private Long plantId;
    private Long articleId;
    private String mimeType;
    private Long size;
    private Integer width;
    private Integer height;
    private String uploadedBy;
}

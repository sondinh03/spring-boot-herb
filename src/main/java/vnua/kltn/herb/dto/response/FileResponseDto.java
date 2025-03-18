package vnua.kltn.herb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileResponseDto {
    private Long id;
    private String name;
    private String path;
    private String originalName;
    private String description;
    private String type;
    private String mimeType;
    private Long size;
    private Integer width;
    private Integer height;
    private LocalDateTime uploadDate;
    private String uploadedBy;
}

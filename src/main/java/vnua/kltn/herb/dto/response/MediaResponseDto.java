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
public class MediaResponseDto {
    private Long id;
    private String fileName;
    private String filePath;
    private String originalName;
    private String description;
    private String fileType;
    private String mimeType;
    private Long fileSize;
    private Integer width;
    private Integer height;
    private LocalDateTime uploadDate;
    private String uploadedBy;
}

package vnua.kltn.herb.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "media")
public class Media extends BaseEntity {
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "url_file")
    private String urlFile;

    @Column(name = "file_type", nullable = false)
    private Integer fileType;

    @Column(name = "file_size", nullable = false)
    private Integer fileSize;

    @Column(name = "alt_text")
    private String altText;
}

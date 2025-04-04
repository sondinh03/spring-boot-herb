package vnua.kltn.herb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "media")
@Builder
public class Media extends BaseEntity {
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "file_type", nullable = false)
    private Integer fileType;

    @Column(name = "file_size", nullable = false)
    private Integer fileSize;

    @Column(name = "alt_text")
    private String altText;

    @Column(name = "uploaded_by")
    private Long uploadedBy;

    /*
    @OneToMany(mappedBy = "featuredImage")
    private List<Article> articles;

    @OneToMany(mappedBy = "avatar")
    private List<Expert> experts;

    @ManyToMany(mappedBy = "media")
    private List<Plant> plants;
    */
}

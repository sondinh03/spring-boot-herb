package vnua.kltn.herb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "articles")
public class Article extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String excerpt;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "featured_image")
    private Long featuredImage;

    @Column(nullable = false)
    private Integer status = 1; // Default: DRAFT

    @Column(name = "is_featured")
    private Boolean isFeatured = false;

    @Column(name = "allow_comments")
    private Boolean allowComments = true;

    @Column(name = "views")
    private Long views;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "disease_id")
    private Long diseaseId;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;
}

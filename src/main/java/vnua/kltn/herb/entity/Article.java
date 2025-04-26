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

    @ManyToOne
    @JoinColumn(name = "featured_image")
    private Media featuredImage;

    @Column(nullable = false)
    private Integer status = 1; // Default: DRAFT

    @Column(name = "is_featured")
    private Boolean isFeatured = false;

    @Column(name = "allow_comments")
    private Boolean allowComments = true;

    private Integer views = 0;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Diseases category;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    /*
    @ManyToMany
    @JoinTable(
            name = "article_tags",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @ManyToMany
    @JoinTable(
            name = "article_plants",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "plant_id")
    )
    private List<Plant> plants;

    @OneToMany(mappedBy = "article")
    private List<Comment> comments;

     */
}

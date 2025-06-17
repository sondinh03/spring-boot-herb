package vnua.kltn.herb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "article_media")
@Builder
public class ArticleMedia {
    @EmbeddedId
    private ArticleMediaId id; // Composite key

    @Column(name = "is_featured")
    private Boolean isFeatured;
}

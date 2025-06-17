package vnua.kltn.herb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ArticleMediaId implements Serializable {
    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "media_id")
    private Long mediaId;
}

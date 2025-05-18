package vnua.kltn.herb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vnua.kltn.herb.utils.SlugGenerator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "diseases")
public class Diseases extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    private String description;

    @Column(name = "parent_id")
    private Long parentId;

    public void setSlug(String slug) {
        if (slug == null || slug.isEmpty()) {
            this.slug = SlugGenerator.generateSlug(this.name);
        }
    }
}

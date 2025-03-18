package vnua.kltn.herb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "disease_group")
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseGroup extends BaseEntity {
    @Column(name = "group_name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "url_slug")
    private String slug;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "is_active")
    private Boolean isActive;
}

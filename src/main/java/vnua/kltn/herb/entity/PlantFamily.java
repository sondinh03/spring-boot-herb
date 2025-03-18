package vnua.kltn.herb.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Table(name = "plant_family")
@NoArgsConstructor
@AllArgsConstructor
public class PlantFamily extends BaseEntity {
    @Column(name = "family_name")
    private String name;

    @Column(name = "scientific_name")
    private String scientificName;

    @Column(name = "description")
    private String description;

    @Column(name = "url_slug")
    private String slug;

    @Column(name = "is_active")
    private Boolean isActive;
}

package vnua.kltn.herb.entity;

import jakarta.persistence.Column;
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
@Table(name = "plant_media")
@Builder
public class PlantMedia extends BaseEntity {
    @Column(name = "plant_id")
    private Long plantId;

    @Column(name = "media_id")
    private Long mediaId;

    @Column(name = "is_featured")
    private Boolean isFeatured;
}

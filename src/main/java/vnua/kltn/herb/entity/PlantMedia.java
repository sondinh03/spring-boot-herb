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
@Table(name = "plant_media")
@Builder
public class PlantMedia{
    @EmbeddedId
    private PlantMediaId id; // Composite key

    @Column(name = "is_featured")
    private Boolean isFeatured;
}

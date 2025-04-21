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
public class PlantMediaId implements Serializable {
    @Column(name = "plant_id")
    private Long plantId;

    @Column(name = "media_id")
    private Long mediaId;
}

package vnua.kltn.herb.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "plant_iamge")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlantImage {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plant_id")
    private Long plantId;

    @Column(name = "file_id")
    private Long fileId;

    @Column(name = "is_primary")
    private Boolean isPrimary;
}

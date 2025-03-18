package vnua.kltn.herb.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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

    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    @Column(name = "caption")
    private String caption;

    @Column(name = "image_type")
    private Integer imageType;

    @Column(name = "display_order")
    private Integer displayOrder;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;
}

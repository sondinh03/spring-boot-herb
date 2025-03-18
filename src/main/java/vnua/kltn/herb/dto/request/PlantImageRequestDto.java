package vnua.kltn.herb.dto.request;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlantImageRequestDto {
    private Long plantId;
    private Long imageId;
    private Boolean isPrimary;
    private String caption;
    private Integer imageType;
    private Integer displayOrder;
    private LocalDateTime createDate;
}

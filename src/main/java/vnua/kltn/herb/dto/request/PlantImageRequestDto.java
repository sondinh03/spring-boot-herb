package vnua.kltn.herb.dto.request;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlantImageRequestDto {
    private Long plantId;
    private Long fileId;
    private Boolean isPrimary;
}

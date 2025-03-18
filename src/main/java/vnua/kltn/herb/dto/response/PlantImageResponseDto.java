package vnua.kltn.herb.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantImageResponseDto {
    private Long id;
    private Long plantId;
    private Long imageId;
    private Boolean isPrimary;
    private String caption;
    private Integer imageType;
    private Integer displayOrder;
    private LocalDateTime createDate;
}

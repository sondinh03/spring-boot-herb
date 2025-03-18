package vnua.kltn.herb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vnua.kltn.herb.dto.BaseObjectDto;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseGroupResponseDto extends BaseObjectDto {
    private Long id;
    private String name;
    private String description;
    private String slug;
    private Integer displayOrder;
    private Boolean isActive;
}

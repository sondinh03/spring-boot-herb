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
public class PlantFamilyResponseDto extends BaseObjectDto {
    private Long id;
    private String name;
    private String scientificName;
    private String description;
    private String slug;
    private Boolean isActive;
}

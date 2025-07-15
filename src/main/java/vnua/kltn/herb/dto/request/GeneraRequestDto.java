package vnua.kltn.herb.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vnua.kltn.herb.dto.BaseObjectDto;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GeneraRequestDto extends BaseObjectDto {
    private Long familyId;
    private String name;
    private String slug;
    private String description;
}

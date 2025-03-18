package vnua.kltn.herb.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vnua.kltn.herb.dto.BaseObjectDto;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseGroupRequestDto extends BaseObjectDto {
    private Long id;
    private String name;
    private String description;
    private String slug;
    private Integer displayOrder;
    private Boolean isActive;
}

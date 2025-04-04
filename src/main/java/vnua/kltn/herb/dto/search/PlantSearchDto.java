package vnua.kltn.herb.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantSearchDto {
    private String keyword;
    private String name;
    private String scientificName;
    private String family;
    private String genus;
    private List<Long> categoryIds;
    private List<Long> tagIds;
    private Integer status;
    private Boolean featured;
}

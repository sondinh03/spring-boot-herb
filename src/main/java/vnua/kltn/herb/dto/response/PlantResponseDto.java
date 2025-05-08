package vnua.kltn.herb.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vnua.kltn.herb.dto.CategoryDto;
import vnua.kltn.herb.dto.MediaDto;
import vnua.kltn.herb.dto.TagDto;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantResponseDto extends BaseResponseDto {
    private Long id;
    private String name;
    private String scientificName;
    private String slug;
    private String family;
    private String genus;
    private String otherNames;
    private String partsUsed;
    private String description;
    private String botanicalCharacteristics;
    private String chemicalComposition;
    private String distribution;
    private String altitude;
    private String harvestSeason;
    private String ecology;
    private String medicinalUses;
    private String indications;
    private String contraindications;
    private String dosage;
    private String folkRemedies;
    private String sideEffects;
    private Long diseaseId;
    private Integer status;
    private Boolean featured;
    private Integer views;
    private Long featuredMediaId;

    private List<CategoryDto> categories;
    private List<TagDto> tags;
    private List<MediaDto> media;
}

package vnua.kltn.herb.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vnua.kltn.herb.dto.CategoryDto;
import vnua.kltn.herb.dto.MediaDto;
import vnua.kltn.herb.dto.TagDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantRequestDto {
    @NotBlank(message = "Tên cây thuốc không được để trống")
    private String name;

    @NotBlank(message = "Tên khoa học không được để trống")
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
    private Long diseasesId;
    private Integer status;
    private Boolean featured;
    private Integer views;
    private Integer createdById;
    private String createdByUsername;
    private String createdAt;
    private String updatedAt;
//    private List<CategoryDto> categories;
//    private List<TagDto> tags;
//    private List<MediaDto> media;
//    private MediaDto featuredImage;
}

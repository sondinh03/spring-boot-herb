package vnua.kltn.herb.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vnua.kltn.herb.dto.BaseObjectDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlantRequestDto extends BaseObjectDto {
    private Long id;
    private String vietnameseName;
    private String scientificName;
    private String otherName;
    private Long planFamilyId;
    private Long diseaseGroupId;
    private List<Long> imageIds;
    private String imageUrl;
    private String uses;
    private String description;
    private String distribution;
    private String content;
    private Long searchCount;
    private String chemicalComposition;
    private String medicinalProperties;
    private String harvestingProcessing;
    private String dosage;
    private String contraindications;
    private String slug;
    private Boolean isFeatured;
    private Boolean isPublished;
}

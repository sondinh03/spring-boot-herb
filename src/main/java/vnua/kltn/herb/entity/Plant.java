package vnua.kltn.herb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "plant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plant extends BaseEntity {
    @Column(name = "vietnamese_name")
    private String vietnameseName;

    @Column(name = "scientific_name")
    private String scientificName;

    @Column(name = "other_name")
    private String otherName;

    @Column(name = "plant_family_id")
    private Long planFamilyId;

    @Column(name = "disease_group_id")
    private Long diseaseGroupId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "uses")
    private String uses;

    @Column(name = "description")
    private String description;

    @Column(name = "distribution")
    private String distribution;

    @Column(name = "content")
    private String content;

    @Column(name = "chemical_composition")
    private String chemicalComposition;

    @Column(name = "medicinal_properties")
    private String medicinalProperties;

    @Column(name = "harvesting_processing")
    private String harvestingProcessing;

    @Column(name = "dosage")
    private String dosage;

    @Column(name = "contraindications")
    private String contraindications;

    @Column(name = "url_slug")
    private String slug;

    @Column(name = "search_count")
    private Long searchCount;

    @Column(name = "is_featured")
    private Boolean isFeatured;

    @Column(name = "is_published")
    private Boolean isPublished;
}

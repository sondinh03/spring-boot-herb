package vnua.kltn.herb.entity;

import jakarta.persistence.CollectionTable;
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
public class Plant extends BaseLogEntity {
    @Column(name = "vietnamese_name")
    private String vietnameseName;

    @Column(name = "scientific_name")
    private String scientificName;

    @Column(name = "plant_family_id")
    private Long planFamilyId;

    @Column(name = "disease_group_id")
    private Long diseaseGroupId;

    @Column(name = "image_url")
    private String image;

    @Column(name = "uses")
    private String uses;

    @Column(name = "description")
    private String description;

    @Column(name = "distribution")
    private String distribution;

    @Column(name = "content")
    private String content;

    @Column(name = "search_count")
    private Long searchCount;
}

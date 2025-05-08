package vnua.kltn.herb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plants")
public class Plant extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(name = "scientific_name", nullable = false)
    private String scientificName;

    @Column(nullable = false, unique = true)
    private String slug;

    private String family;

    private String genus;

    @Column(name = "other_names")
    private String otherNames;

    @Column(name = "parts_used")
    private String partsUsed;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "botanical_characteristics", columnDefinition = "TEXT")
    private String botanicalCharacteristics;

    @Column(name = "chemical_composition", columnDefinition = "TEXT")
    private String chemicalComposition;

    @Column(columnDefinition = "TEXT")
    private String distribution;

    private String altitude;

    @Column(name = "harvest_season")
    private String harvestSeason;

    @Column(columnDefinition = "TEXT")
    private String ecology;

    @Column(name = "medicinal_uses", columnDefinition = "TEXT")
    private String medicinalUses;

    @Column(columnDefinition = "TEXT")
    private String indications;

    @Column(columnDefinition = "TEXT")
    private String contraindications;

    @Column(columnDefinition = "TEXT")
    private String dosage;

    @Column(name = "folk_remedies", columnDefinition = "TEXT")
    private String folkRemedies;

    @Column(name = "side_effects", columnDefinition = "TEXT")
    private String sideEffects;

    @Column(name = "diseases_id")
    private Long diseaseId;

    @Column(nullable = false)
    private Integer status = 1; // Default: DRAFT

    private Boolean featured = false;

    private Integer views = 0;
    /*
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToMany
    @JoinTable(
            name = "plant_categories",
            joinColumns = @JoinColumn(name = "plant_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @ManyToMany
    @JoinTable(
            name = "plant_media",
            joinColumns = @JoinColumn(name = "plant_id"),
            inverseJoinColumns = @JoinColumn(name = "media_id")
    )
    private List<Media> media;

    @ManyToMany
    @JoinTable(
            name = "plant_tags",
            joinColumns = @JoinColumn(name = "plant_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @ManyToMany(mappedBy = "plants")
    private List<Article> articles;

    @ManyToMany(mappedBy = "plants")
    private List<Expert> experts;

    @ManyToMany(mappedBy = "plants")
    private List<Research> research;

     */
}

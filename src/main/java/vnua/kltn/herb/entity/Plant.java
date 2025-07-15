package vnua.kltn.herb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plants")
public class Plant extends BaseEntity {
    /**
     * Tên tiếng Việt của cây dược liệu
     */
    @NotBlank(message = "Tên cây dược liệu không được để trống")
    @Length(min = 2, max = 255, message = "Tên cây dược liệu phải từ 2-255 ký tự")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\s\\-\\.\\(\\)]+$", message = "Tên cây dược liệu chỉ chứa chữ cái, số, dấu cách và các ký tự đặc biệt cơ bản")
    @Column(nullable = false, length = 255)
    private String name;

    /**
     * Tên khoa học của cây dược liệu
     */
    @NotBlank(message = "Tên khoa học không được để trống")
    @Length(min = 5, max = 255, message = "Tên khoa học phải từ 5-255 ký tự")
    @Pattern(regexp = "^[A-Za-z\\s\\-\\.\\(\\)]+$", message = "Tên khoa học chỉ chứa chữ cái Latin, dấu cách và các ký tự đặc biệt cơ bản")
    @Column(name = "scientific_name", nullable = false, length = 255)
    private String scientificName;

    /**
     * Chuỗi định danh duy nhất (slug) của cây dược liệu, dùng để tạo URL thân thiện
     */
    @Column(nullable = false, unique = true)
    private String slug;

    /**
     * Họ thực vật mà cây dược liệu thuộc về
     */
    @Column(name = "family")
    private String family;

    /**
     * ID của họ thực vật trong cơ sở dữ liệu
     */
    @Column(name = "family_id")
    private Long familyId;

    /**
     * Chi (genus) mà cây dược liệu thuộc về
     */
    @Column(name = "genus")
    private String genus;

    /**
     * ID của chi (genus) trong cơ sở dữ liệu
     */
    @Column(name = "genera_id")
    private Long generaId;

    /**
     * Các tên khác (biệt danh) của cây dược liệu
     */
    @Column(name = "other_names")
    private String otherNames;

    /**
     * Các bộ phận được sử dụng của cây dược liệu (ví dụ: lá, rễ, hoa)
     */
    @Column(name = "parts_used")
    private String partsUsed;

    /**
     * Mô tả tổng quan về cây dược liệu
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Đặc điểm hình thái thực vật học của cây dược liệu
     */
    @Column(name = "botanical_characteristics", columnDefinition = "TEXT")
    private String botanicalCharacteristics;

    /**
     * Thành phần hóa học của cây dược liệu
     */
    @Column(name = "chemical_composition", columnDefinition = "TEXT")
    private String chemicalComposition;

    /**
     * Phân bố địa lý của cây dược liệu
     */
    @Column(columnDefinition = "TEXT")
    private String distribution;

    /**
     * Độ cao (altitude) nơi cây dược liệu sinh trưởng
     */
    @Column(name = "altitude")
    private String altitude;

    /**
     * Mùa thu hoạch của cây dược liệu
     */
    @Column(name = "harvest_season")
    private String harvestSeason;

    /**
     * Đặc điểm sinh thái của cây dược liệu
     */
    @Column(columnDefinition = "TEXT")
    private String ecology;

    /**
     * Công dụng y học của cây dược liệu
     */
    @Column(name = "medicinal_uses", columnDefinition = "TEXT")
    private String medicinalUses;

    /**
     * Các chỉ định sử dụng của cây dược liệu
     */
    @Column(columnDefinition = "TEXT")
    private String indications;

    /**
     * Các chống chỉ định khi sử dụng cây dược liệu
     */
    @Column(columnDefinition = "TEXT")
    private String contraindications;

    /**
     * Liều lượng sử dụng cây dược liệu
     */
    @Column(columnDefinition = "TEXT")
    private String dosage;

    /**
     * Các bài thuốc dân gian sử dụng cây dược liệu
     */
    @Column(name = "folk_remedies", columnDefinition = "TEXT")
    private String folkRemedies;

    /**
     * Các tác dụng phụ khi sử dụng cây dược liệu
     */
    @Column(name = "side_effects", columnDefinition = "TEXT")
    private String sideEffects;

    /**
     * ID của bệnh liên quan mà cây dược liệu điều trị (liên kết với bảng bệnh)
     */
    @Column(name = "diseases_id")
    private Long diseaseId;

    /**
     * Trạng thái của cây dược liệu (1: Nháp, mặc định)
     */
    @Column(nullable = false)
    private Integer status = 1; // Default: DRAFT

    private Boolean featured = false;

    /**
     * Số lượt xem của cây dược liệu
     */
    private Integer views = 0;

    private String stemDescription;

    private String leafDescription;

    private String flowerDescription;

    private String fruitDescription;

    private String rootDescription;

    private Long activeCompoundId;

    private Long dataSourceId;

    private Integer favoritesCount;
}

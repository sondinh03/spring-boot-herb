package vnua.kltn.herb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "experts")
public class Expert extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    private String title;

    private String specialization;

    private String institution;

    @Column(columnDefinition = "TEXT")
    private String education;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(columnDefinition = "TEXT")
    private String achievements;

    @Column(name = "contact_email")
    private String contactEmail;

    private String zaloPhone;

    @Column(name = "avatar")
    private Long avatarId;

    @Column(nullable = false)
    private Integer status = 1; // Default: active
}

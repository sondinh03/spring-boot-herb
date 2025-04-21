package vnua.kltn.herb.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "role_type", nullable = false)
    private Integer roleType = 3; // Default: USER

    @Column(nullable = false)
    private Integer status = 1; // Default: active

    // Relationships
    /*
    @OneToMany(mappedBy = "author")
    private List<Article> articles;

    @OneToMany(mappedBy = "createdBy")
    private List<Plant> plants;

    @OneToMany(mappedBy = "createdBy")
    private List<Research> research;

    @OneToMany(mappedBy = "uploadedBy")
    private List<Media> media;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;
     */
}

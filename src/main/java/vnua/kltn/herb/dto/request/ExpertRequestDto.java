package vnua.kltn.herb.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vnua.kltn.herb.dto.BaseObjectDto;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExpertRequestDto extends BaseObjectDto {
    private String name;
    private String slug;
    private String title;
    private String specialization;
    private String institution;
    private String education;
    private String bio;
    private String achievements;
    private String contactEmail;
    private String zaloPhone;
    private Long avatarId;
    private Integer status = 1;
}

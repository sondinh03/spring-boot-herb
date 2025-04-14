package vnua.kltn.herb.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
    private Long id;

    @NotBlank(message = "Tên tag không được để trống")
    private String name;

    private String slug;
    private String createdAt;
}

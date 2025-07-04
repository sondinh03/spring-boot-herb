package vnua.kltn.herb.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceRequestDto {
    @NotBlank(message = "Tên không được để trống")
    private String name;
    private String slug;
    private Integer typeSource;
    private String description;
    private String author;
    private String publisher;
    private Integer publicationYear;
    private String url;
    private String isbnIssn;
}

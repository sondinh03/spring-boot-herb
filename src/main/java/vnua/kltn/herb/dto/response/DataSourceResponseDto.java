package vnua.kltn.herb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceResponseDto extends BaseResponseDto{
    private Long id;
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

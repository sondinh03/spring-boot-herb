package vnua.kltn.herb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vnua.kltn.herb.dto.CategoryDto;
import vnua.kltn.herb.dto.MediaDto;
import vnua.kltn.herb.dto.TagDto;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResearchPurchaseResponseDto extends BaseResponseDto {
    private Long id;
    private Long userId;
    private Long researchId;
}

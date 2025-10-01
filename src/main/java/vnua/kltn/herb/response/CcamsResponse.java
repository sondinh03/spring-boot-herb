package vnua.kltn.herb.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vnua.kltn.herb.entity.CotDiem;
import vnua.kltn.herb.entity.StudentData;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CcamsResponse {
    @JsonProperty("hocvien")
    private List<StudentData> hocVien;

    @JsonProperty("cotdiems")
    private List<CotDiem> cotDiems;
}

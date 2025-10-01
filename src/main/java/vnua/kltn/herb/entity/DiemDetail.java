package vnua.kltn.herb.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiemDetail {
    @JsonProperty("MACOTDIEM")
    private String maCotDiem;

    @JsonProperty("DIEMDAT")
    private Double diemDat;
}

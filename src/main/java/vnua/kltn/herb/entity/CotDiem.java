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
public class CotDiem {
    @JsonProperty("MACOTDIEM")
    private Integer maCotDiem;

    @JsonProperty("TENCOTDIEM")
    private String tenCotDiem;

    @JsonProperty("HESOTINH")
    private String heSoTinh;
}

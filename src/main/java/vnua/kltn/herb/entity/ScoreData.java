package vnua.kltn.herb.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScoreData {
    @JsonProperty("DIEMDAT")
    private Double diemDat;

    @JsonProperty("HESO")
    private String heSo;
}

package vnua.kltn.herb.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vnua.kltn.herb.service.ScoreDataDeserializer;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentData {
    @JsonProperty("MAHOCVIEN")
    private String maHocvien;

    @JsonProperty("TENTHANH")
    private String tenThanh;

    @JsonProperty("HOCANHAN")
    private String ho;

    @JsonProperty("TENCANHAN")
    private String ten;

    @JsonProperty("TENKHOI")
    private String tenKhoi;

    @JsonProperty("TENLOPHOC")
    private String tenLopHoc;

    @JsonProperty("TENNIENHOC")
    private String nienHoc;

    @JsonProperty("data")
    @JsonDeserialize(using = ScoreDataDeserializer.class)
    private Map<String, ScoreData> data;

    @JsonProperty("diems")
    private List<DiemDetail> diems;
}

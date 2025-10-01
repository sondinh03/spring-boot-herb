package vnua.kltn.herb.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import vnua.kltn.herb.response.CcamsResponse;
import vnua.kltn.herb.service.ExcelExportService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/export")
@AllArgsConstructor
public class ExportController {
    private final ExcelExportService excelExportService;

    private final RestTemplate restTemplate;

    @GetMapping("/students/excel")
    public ResponseEntity<ByteArrayResource> exportStudentsToExcel(
            @RequestParam(required = false) String sourceApiUrl,
            @RequestParam(required = false) String token,
            @RequestParam(required = false) Integer maloaidiem,
            @RequestParam(required = false) Integer manienhoc,
            @RequestParam(required = false) Integer makhoi,
            @RequestParam(required = false) Integer malophoc,
            @RequestParam(required = false) Integer mahocky
    ) throws IOException {
        try {
            String apiUrl;

            // Nếu không truyền sourceApiUrl thì build từ các tham số
            if (sourceApiUrl == null || sourceApiUrl.isEmpty()) {
                StringBuilder urlBuilder = new StringBuilder("https://ccamspro.thongtinxuanloc.com/api/v1/diem?");
                if (maloaidiem != null) urlBuilder.append("MALOAIDIEM=").append(maloaidiem).append("&");
                if (manienhoc != null) urlBuilder.append("MANIENHOC=").append(manienhoc).append("&");
                if (makhoi != null) urlBuilder.append("MAKHOI=").append(makhoi).append("&");
                if (malophoc != null) urlBuilder.append("MALOPHOC=").append(malophoc).append("&");
                if (mahocky != null) urlBuilder.append("MAHOCKY=").append(mahocky).append("&");

                // Remove trailing &
                apiUrl = urlBuilder.toString();
                if (apiUrl.endsWith("&")) {
                    apiUrl = apiUrl.substring(0, apiUrl.length() - 1);
                }
            } else {
                apiUrl = sourceApiUrl;
            }

            HttpHeaders requestHeaders = new HttpHeaders();

            if (token != null && !token.isEmpty()) {
                requestHeaders.set("Authorization", "Bearer " + token);
            }

            HttpEntity<Void> entity = new HttpEntity<>(requestHeaders);

            ResponseEntity<String> stringResponse = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            System.out.println("RAW JSON RESPONSE:");
            System.out.println(stringResponse.getBody());

            ObjectMapper mapper = new ObjectMapper();
            CcamsResponse ccamsResponse = mapper.readValue(stringResponse.getBody(), CcamsResponse.class);

            if (ccamsResponse == null || ccamsResponse.getHocVien() == null || ccamsResponse.getHocVien().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            byte[] excelData = excelExportService.exportStudentScores(ccamsResponse);
            ByteArrayResource resource = new ByteArrayResource(excelData);

            // Tạo tên file động
            var tenLop = "Unknown";
            if (!ccamsResponse.getHocVien().isEmpty()) {
                tenLop = excelExportService.decodeUnicode(ccamsResponse.getHocVien().getFirst().getTenLopHoc()).replaceAll("[^a-zA-Z0-9]", "_");
            }

            // Format datetime: yyyyMMdd_HHmmss
            var datetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            var filenane = String.format("DanhSachDiem_Lop_%s_%s.xlsx", tenLop, datetime);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filenane);
            responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .contentLength(excelData.length)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/students/excel/secure")
    public ResponseEntity<ByteArrayResource> exportStudentsToExcelSecure(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(required = false) String sourceApiUrl,
            @RequestParam(required = false) Integer maloaidiem,
            @RequestParam(required = false) Integer manienhoc,
            @RequestParam(required = false) Integer makhoi,
            @RequestParam(required = false) Integer malophoc,
            @RequestParam(required = false) Integer mahocky
    ) throws IOException {
        try {
            String apiUrl;

            if (sourceApiUrl == null || sourceApiUrl.isEmpty()) {
                StringBuilder urlBuilder = new StringBuilder("https://ccamspro.thongtinxuanloc.com/api/v1/diem?");
                if (maloaidiem != null) urlBuilder.append("MALOAIDIEM=").append(maloaidiem).append("&");
                if (manienhoc != null) urlBuilder.append("MANIENHOC=").append(manienhoc).append("&");
                if (makhoi != null) urlBuilder.append("MAKHOI=").append(makhoi).append("&");
                if (malophoc != null) urlBuilder.append("MALOPHOC=").append(malophoc).append("&");
                if (mahocky != null) urlBuilder.append("MAHOCKY=").append(mahocky).append("&");

                apiUrl = urlBuilder.toString();
                if (apiUrl.endsWith("&")) {
                    apiUrl = apiUrl.substring(0, apiUrl.length() - 1);
                }
            } else {
                apiUrl = sourceApiUrl;
            }

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.set("Authorization", authHeader);

            HttpEntity<String> entity = new HttpEntity<>(requestHeaders);

            ResponseEntity<CcamsResponse> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    entity,
                    CcamsResponse.class
            );

            CcamsResponse ccamsResponse = response.getBody();

            if (ccamsResponse == null || ccamsResponse.getHocVien() == null) {
                return ResponseEntity.badRequest().build();
            }

            byte[] excelData = excelExportService.exportStudentScores(ccamsResponse);
            ByteArrayResource resource = new ByteArrayResource(excelData);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=diem_hoc_vien.xlsx");
            responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .contentLength(excelData.length)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}

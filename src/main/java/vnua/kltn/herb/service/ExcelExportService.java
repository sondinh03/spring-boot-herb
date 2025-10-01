package vnua.kltn.herb.service;

import vnua.kltn.herb.response.CcamsResponse;

import java.io.IOException;

public interface ExcelExportService {
    // Method để decode Unicode escape sequences
    String decodeUnicode(String text);

    byte[] exportStudentScores(CcamsResponse ccamsResponse) throws IOException;
}

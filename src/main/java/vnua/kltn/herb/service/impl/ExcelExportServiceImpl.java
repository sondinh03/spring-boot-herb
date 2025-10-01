package vnua.kltn.herb.service.impl;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import vnua.kltn.herb.entity.CotDiem;
import vnua.kltn.herb.entity.ScoreData;
import vnua.kltn.herb.entity.StudentData;
import vnua.kltn.herb.response.CcamsResponse;
import vnua.kltn.herb.service.ExcelExportService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExcelExportServiceImpl implements ExcelExportService {
    private static final String FONT_NAME = "Times New Roman";
    private static final short TITLE_FONT_SIZE = 15;
    private static final short SUBTITLE_FONT_SIZE = 17;
    private static final short NORMAL_FONT_SIZE = 13;
    private static final double COLUMN_WIDTH_MULTIPLIER  = 1.2;
    private static final float TITLE_ROW_HEIGHT_MULTIPLIER  = 2.0f;
    private static final float SUBTITLE_ROW_HEIGHT_MULTIPLIER  = 3.0f;

    @Override
    public String decodeUnicode(String text) {
        if (text == null) {
            return "";
        }
        try {
            return org.apache.commons.text.StringEscapeUtils.unescapeJava(text);
        } catch (Exception e) {
            return text;
        }
    }

    @Override
    public byte[] exportStudentScores(CcamsResponse ccamsResponse) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Điểm học viên");

        // Tạo tất cả styles trước
        CellStyle titleStyle = createTitleStyle(workbook);
        CellStyle subtitleStyle = createSubtitleStyle(workbook);
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle dataCellStyle = createDataCellStyle(workbook);
        CellStyle nameCellStyle = createNameCellStyle(workbook);
//        CellStyle outerBorderStyle = createOuterBorderStyle(workbook);

        // Tạo map để tra cứu tên cột điểm (với decode Unicode)
        Map<String, String> cotDiemMap = new HashMap<>();
        for (CotDiem cotDiem : ccamsResponse.getCotDiems()) {
            String decodeName = decodeUnicode(cotDiem.getTenCotDiem());
            cotDiemMap.put(cotDiem.getMaCotDiem().toString(), decodeName);
        }

        // Lấy thông tin cho subtitle
        var tenKhoi = "";
        var tenLop = "";
        var nienHoc = "";

        if (!ccamsResponse.getHocVien().isEmpty()) {
            var student = ccamsResponse.getHocVien().getFirst();
            tenKhoi = student.getTenKhoi() != null ? decodeUnicode(student.getTenKhoi()) : "";
            tenLop = student.getTenLopHoc() != null ? decodeUnicode(student.getTenLopHoc()) : "";
            nienHoc = student.getNienHoc() != null ? decodeUnicode(student.getNienHoc()) : "";
        }

        // Tạo header
        var titleRow = sheet.createRow(1);
        titleRow.setHeightInPoints(TITLE_ROW_HEIGHT_MULTIPLIER * sheet.getDefaultRowHeightInPoints());
        var titleCell = titleRow.createCell(0);
        titleCell.setCellValue("HẢI NHUẬN");
        titleCell.setCellStyle(titleStyle);

        var subtitleRow = sheet.createRow(2);
        subtitleRow.setHeightInPoints(SUBTITLE_ROW_HEIGHT_MULTIPLIER * sheet.getDefaultRowHeightInPoints());
        var subtitleCell = subtitleRow.createCell(0);
        subtitleCell.setCellValue("Danh sách điểm " + tenKhoi + " - " + tenLop + "\n" + nienHoc);
        subtitleCell.setCellStyle(subtitleStyle);

        // Tạo header row
        /*
        Row headerRow = sheet.createRow(4);
        Cell sttCell = headerRow.createCell(0);
        sttCell.setCellValue("Stt");
        sttCell.setCellStyle(headerStyle);

        Cell maHocVien = headerRow.createCell(1);
        maHocVien.setCellValue("Mã số");
        maHocVien.setCellStyle(headerStyle);

        Cell tenThanhCell = headerRow.createCell(2);
        tenThanhCell.setCellValue("Tên thánh");
        tenThanhCell.setCellStyle(headerStyle);

        Cell hoCell = headerRow.createCell(3);
        hoCell.setCellValue("Họ");
        hoCell.setCellStyle(headerStyle);

        Cell tenCell = headerRow.createCell(4);
        tenCell.setCellValue("Tên");
        tenCell.setCellStyle(headerStyle);


        int colIndex = 5;
        int lastCol = 0;
        Map<String, Integer> columnIndexMap = new HashMap<>();

        for (StudentData student : ccamsResponse.getHocVien()) {
            if (student.getData() != null) {
                for (String maCotDiem : student.getData().keySet()) {
                    if (!columnIndexMap.containsKey(maCotDiem)) {
                        String tenCot = cotDiemMap.get(maCotDiem);
                        Cell headerCell = headerRow.createCell(colIndex);
                        headerCell.setCellValue(tenCot);
                        headerCell.setCellStyle(headerStyle);
                        columnIndexMap.put(maCotDiem, colIndex);
                        colIndex++;
                    }
                }
                lastCol = colIndex - 1;
            }
        }

         */
        // Tạo header row và lấy column mapping
        Map<String, Integer> columnIndexMap = createHeaderRow(
                sheet,
                headerStyle,
                ccamsResponse.getHocVien(),
                cotDiemMap
        );

        int lastCol = columnIndexMap.isEmpty() ? 4 : columnIndexMap.values().stream().max(Integer::compareTo).orElse(0);

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, lastCol));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, lastCol));

        /*
        int rowIndex = 5;
        int stt = 1;
        for (StudentData student : ccamsResponse.getHocVien()) {
            Row dataRow = sheet.createRow(rowIndex++);

            setCell(dataRow, 0, String.valueOf(stt++), dataCellStyle);
            setCell(dataRow, 1, student.getMaHocvien(), dataCellStyle);
            setCell(dataRow, 2, student.getTenThanh(), dataCellStyle);
            setCell(dataRow, 3, decodeUnicode(student.getHo()), dataCellStyle);
            setCell(dataRow, 4, decodeUnicode(student.getTen()), nameCellStyle);

            if (student.getData() != null) {
                for (Map.Entry<String, ScoreData> entry : student.getData().entrySet()) {
                    String maCotDiem = entry.getKey();
                    ScoreData scoreData = entry.getValue();

                    Integer colIdx = columnIndexMap.get(maCotDiem);
                    var diem = scoreData.getDiemDat();
                    if (colIdx != null && diem != null) {
                        setCell(dataRow, colIdx, diem, dataCellStyle);
                    }
                }
            }
        }

         */
        int lastRow = populateStudentData(
                sheet,
                ccamsResponse.getHocVien(),
                columnIndexMap,
                dataCellStyle,
                nameCellStyle
        );

        for (int i = 0; i <= lastCol; i++) {
            sheet.autoSizeColumn(i);
            var currentWidth = sheet.getColumnWidth(i);
            sheet.setColumnWidth(i, (int) (currentWidth * COLUMN_WIDTH_MULTIPLIER));
        }

        applyOuterBorder(sheet, 4, lastRow, 0, lastCol);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }

    private CellStyle createTitleStyle(Workbook workbook) {
        var style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints(TITLE_FONT_SIZE);
        font.setFontName(FONT_NAME);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private CellStyle createSubtitleStyle(Workbook workbook) {
        var style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints(SUBTITLE_FONT_SIZE);
        font.setColor(IndexedColors.RED.getIndex());
        font.setFontName(FONT_NAME);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        return style;
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints(NORMAL_FONT_SIZE);
        font.setFontName(FONT_NAME);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        setBorderedStyle(style);
        return style;
    }

    private CellStyle createDataCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints(NORMAL_FONT_SIZE);
        font.setFontName(FONT_NAME);
        style.setFont(font);
        setBorderedStyle(style);
        return style;
    }

    private CellStyle createNameCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints(NORMAL_FONT_SIZE);
        font.setFontName(FONT_NAME);
        style.setFont(font);
        setBorderedStyle(style);
        return style;
    }

//    private CellStyle createOuterBorderStyle(Workbook workbook) {
//        CellStyle style = workbook.createCellStyle();
//        style.setBorderTop(BorderStyle.DOUBLE);
//        style.setBorderBottom(BorderStyle.DOUBLE);
//        style.setBorderLeft(BorderStyle.DOUBLE);
//        style.setBorderRight(BorderStyle.DOUBLE);
//        return style;
//    }

    private Map<String, Integer> createHeaderRow(
            Sheet sheet,
            CellStyle headerStyle,
            List<StudentData> hocViens,
            Map<String, String> cotDiemMap) {

        Row headerRow = sheet.createRow(4);

        // Tạo các cột cố định
        createHeaderCell(headerRow, 0, "Stt", headerStyle);
        createHeaderCell(headerRow, 1, "Mã số", headerStyle);
        createHeaderCell(headerRow, 2, "Tên thánh", headerStyle);
        createHeaderCell(headerRow, 3, "Họ", headerStyle);
        createHeaderCell(headerRow, 4, "Tên", headerStyle);

        // Tạo các cột điểm động
        Map<String, Integer> columnIndexMap = new HashMap<>();
        int colIndex = 5;

        for (StudentData student : hocViens) {
            if (student.getData() != null) {
                for (String maCotDiem : student.getData().keySet()) {
                    if (!columnIndexMap.containsKey(maCotDiem)) {
                        String tenCot = cotDiemMap.get(maCotDiem);
                        createHeaderCell(headerRow, colIndex, tenCot, headerStyle);
                        columnIndexMap.put(maCotDiem, colIndex);
                        colIndex++;
                    }
                }
            }
        }

        return columnIndexMap;
    }

    private void createHeaderCell(Row row, int colIndex, String value, CellStyle style) {
        Cell cell = row.createCell(colIndex);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private int populateStudentData(
            Sheet sheet,
            List<StudentData> hocViens,
            Map<String, Integer> columnIndexMap,
            CellStyle dataCellStyle,
            CellStyle nameCellStyle) {

        int rowIndex = 5;
        int stt = 1;

        int maxCol = columnIndexMap.isEmpty() ? 4 :
                columnIndexMap.values().stream().max(Integer::compare).orElse(4);

        for (StudentData student : hocViens) {
            Row dataRow = sheet.createRow(rowIndex++);

            // Tạo các cell cố định
            setCell(dataRow, 0, String.valueOf(stt++), dataCellStyle);
            setCell(dataRow, 1, student.getMaHocvien(), dataCellStyle);
            setCell(dataRow, 2, student.getTenThanh(), dataCellStyle);
            setCell(dataRow, 3, decodeUnicode(student.getHo()), dataCellStyle);
            setCell(dataRow, 4, decodeUnicode(student.getTen()), nameCellStyle);

            // Tạo TẤT CẢ các cell điểm với style (kể cả rỗng)
            for (int col = 5; col <= maxCol; col++) {
                setCell(dataRow, col, (String) null, dataCellStyle);
            }

            // Điền dữ liệu điểm
            if (student.getData() != null) {
                for (Map.Entry<String, ScoreData> entry : student.getData().entrySet()) {
                    String maCotDiem = entry.getKey();
                    ScoreData scoreData = entry.getValue();
                    Integer colIdx = columnIndexMap.get(maCotDiem);

                    if (colIdx != null && scoreData.getDiemDat() != null) {
                        setCell(dataRow, colIdx, scoreData.getDiemDat(), dataCellStyle);
                    }
                }
            }
        }

        return rowIndex - 1; // Trả về index của row cuối cùng
    }

    private void applyOuterBorder(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        for (int row = firstRow; row <= lastRow; row++) {
            Row r = sheet.getRow(row);
            if (r == null) continue;

            for (int col = firstCol; col <= lastCol; col++) {
                Cell cell = r.getCell(col);
                if (cell == null) continue;

                CellStyle newStyle = sheet.getWorkbook().createCellStyle();
                newStyle.cloneStyleFrom(cell.getCellStyle());

                // Hàng đầu tiên
                if (row == firstRow) {
                    newStyle.setBorderTop(BorderStyle.DOUBLE);
                }
                // Hàng cuối cùng
                if (row == lastRow) {
                    newStyle.setBorderBottom(BorderStyle.DOUBLE);
                }
                // Cột đầu tiên
                if (col == firstCol) {
                    newStyle.setBorderLeft(BorderStyle.DOUBLE);
                }
                // Cột cuối cùng
                if (col == lastCol) {
                    newStyle.setBorderRight(BorderStyle.DOUBLE);
                }

                cell.setCellStyle(newStyle);
            }
        }
    }

    private void setCell(Row row, int columnIndex, String value, CellStyle style) {
        var cell = row.createCell(columnIndex);
        if (value != null) {
            cell.setCellValue(value);
        }
        cell.setCellStyle(style);
    }

    private void setCell(Row row, int columnIndex, Double value, CellStyle style) {
        var cell = row.createCell(columnIndex);
        if (value != null) {
            cell.setCellValue(value);
        }
        cell.setCellStyle(style);
    }

    private void setBorderedStyle(CellStyle style) {
        // Set border
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }
}

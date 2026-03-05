package ru.khehelk.practice.export.file.xlsx;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import ru.khehelk.practice.export.file.FileExportConfig;
import ru.khehelk.practice.export.file.FileExportFormat;
import ru.khehelk.practice.export.file.FileExportStrategy;

public class XlsxExportStrategy implements FileExportStrategy {

    private static final int WINDOW_SIZE = 500;

    @Override
    public FileExportFormat getExportFormat() {
        return FileExportFormat.XLSX;
    }

    @Override
    public <T> void export(FileExportConfig<T> config) throws IOException {
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(WINDOW_SIZE)) {
            var xlsxConfig = (XlsxExportConfig<T>) config;
            workbook.setCompressTempFiles(true);

            Sheet sheet = workbook.createSheet(xlsxConfig.getSheetName());

            createHeader(sheet, xlsxConfig.getHeaders());

            CellStyle dateStyle = createDateStyle(workbook);

            AtomicInteger rowIndex = new AtomicInteger(1);

            xlsxConfig.getReport().accept(rowObj ->
                createRow(rowObj, rowIndex, dateStyle, sheet, xlsxConfig));

            workbook.write(xlsxConfig.getOutputStream());
        }
    }

    private <T> void createRow(T rowObj,
                               AtomicInteger rowIndex,
                               CellStyle dateStyle,
                               Sheet sheet,
                               XlsxExportConfig<T> xlsxConfig) {
        int rowNum = rowIndex.getAndIncrement();
        Row row = sheet.createRow(rowNum);
        var columns = xlsxConfig.getColumnMetas();

        for (int col = 0; col < columns.size(); col++) {

            XlsxColumnDescriptor<T> column = columns.get(col);
            Object value = column.extract(rowObj);

            if (value == null) continue;

            Cell cell = row.createCell(col);

            switch (column.getCellType()) {
                case STRING -> cell.setCellValue(value.toString());
                case NUMBER ->
                    cell.setCellValue(
                        ((Number) value).doubleValue()
                    );
                case DATE -> {
                    cell.setCellValue((LocalDate) value);
                    cell.setCellStyle(dateStyle);
                }
                case DATETIME -> {
                    cell.setCellValue((LocalDateTime) value);
                    cell.setCellStyle(dateStyle);
                }
            }
        }
    }

    private void createHeader(
        Sheet sheet,
        List<String> columns
    ) {
        Row header = sheet.createRow(0);

        for (int i = 0; i < columns.size(); i++) {
            header.createCell(i)
                .setCellValue(columns.get(i));
        }
    }

    private CellStyle createDateStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        CreationHelper helper = workbook.getCreationHelper();
        style.setDataFormat(
            helper.createDataFormat()
                .getFormat("yyyy-mm-dd hh:mm:ss")
        );
        return style;
    }
}

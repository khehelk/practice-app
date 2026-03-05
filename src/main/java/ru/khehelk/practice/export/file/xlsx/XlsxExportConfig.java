package ru.khehelk.practice.export.file.xlsx;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import lombok.Builder;
import lombok.Getter;
import ru.khehelk.practice.export.file.FileExportConfig;
import ru.khehelk.practice.export.file.FileExportFormat;

@Getter
@Builder
public class XlsxExportConfig<T> implements FileExportConfig<T> {

    private final String sheetName;
    private final OutputStream outputStream;
    private final List<XlsxColumnDescriptor<T>> columnMetas;
    private final Consumer<Consumer<T>> report;

    @Override
    public FileExportFormat getExportFormat() {
        return FileExportFormat.XLSX;
    }

    public List<String> getHeaders() {
        List<String> headers = new ArrayList<>();
        for (var columnMeta : columnMetas) {
            headers.add(columnMeta.getHeader());
        }
        return headers;
    }
}

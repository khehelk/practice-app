package ru.khehelk.practice.export.file;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.khehelk.practice.export.file.csv.CsvExportStrategy;
import ru.khehelk.practice.export.file.xlsx.XlsxExportStrategy;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileExportContext {

    private static final Map<FileExportFormat, FileExportStrategy> strategies = new EnumMap<>(FileExportFormat.class);

    static {
        var csvExportStrategy = new CsvExportStrategy();
        strategies.put(csvExportStrategy.getExportFormat(), csvExportStrategy);
        var xlsxExportStrategy = new XlsxExportStrategy();
        strategies.put(xlsxExportStrategy.getExportFormat(), xlsxExportStrategy);
    }

    public static <T> void export(FileExportConfig<T> config) throws IOException {
        strategies.get(config.getExportFormat()).export(config);
    }

}

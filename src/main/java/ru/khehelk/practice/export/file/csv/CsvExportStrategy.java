package ru.khehelk.practice.export.file.csv;

import ru.khehelk.practice.export.file.FileExportConfig;
import ru.khehelk.practice.export.file.FileExportFormat;
import ru.khehelk.practice.export.file.FileExportStrategy;

public class CsvExportStrategy implements FileExportStrategy {
    @Override
    public FileExportFormat getExportFormat() {
        return FileExportFormat.CSV;
    }

    @Override
    public <T> void export(FileExportConfig<T> config) {
        CsvExportConfig csvConfig = (CsvExportConfig) config;
    }
}

package ru.khehelk.practice.export.file.csv;

import java.util.function.Consumer;

import ru.khehelk.practice.export.file.FileExportConfig;
import ru.khehelk.practice.export.file.FileExportFormat;

public class CsvExportConfig<T> implements FileExportConfig<T> {
    @Override
    public FileExportFormat getExportFormat() {
        return FileExportFormat.CSV;
    }

    @Override
    public Consumer<Consumer<T>> getReport() {
        return null;
    }
}

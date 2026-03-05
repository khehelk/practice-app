package ru.khehelk.practice.export.file;

import java.util.function.Consumer;

public interface FileExportConfig<T> {
    FileExportFormat getExportFormat();
    Consumer<Consumer<T>> getReport();
}

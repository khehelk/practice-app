package ru.khehelk.practice.export.file;

import java.io.IOException;

public interface FileExportStrategy {
    FileExportFormat getExportFormat();
    <T> void export(FileExportConfig<T> config) throws IOException;
}

package ru.khehelk.practice.domain.dto.request;

import ru.khehelk.practice.domain.enums.Database;
import ru.khehelk.practice.export.file.FileExportFormat;

public record GetEventsReportRequest(
    FileExportFormat format,
    Database db
) {
}

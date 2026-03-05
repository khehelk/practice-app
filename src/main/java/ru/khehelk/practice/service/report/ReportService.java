package ru.khehelk.practice.service.report;

import java.io.OutputStream;
import java.util.function.Consumer;

import ru.khehelk.practice.domain.dto.request.GetEventsReportRequest;
import ru.khehelk.practice.domain.enums.Database;

public interface ReportService {
    Database getDatabase();
    Consumer<OutputStream> getUsersReportFile(GetEventsReportRequest request);
}

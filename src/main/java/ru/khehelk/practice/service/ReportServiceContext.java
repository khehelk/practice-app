package ru.khehelk.practice.service;

import java.io.OutputStream;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;
import ru.khehelk.practice.domain.dto.request.GetEventsReportRequest;
import ru.khehelk.practice.domain.enums.Database;
import ru.khehelk.practice.service.report.ReportService;

@Component
public class ReportServiceContext {

    private static final Map<Database, ReportService> REPORT_SERVICES = new EnumMap<>(Database.class);

    public ReportServiceContext(List<ReportService> reportServices) {
        for (var reportService : reportServices) {
            REPORT_SERVICES.put(reportService.getDatabase(), reportService);
        }
    }

    public Consumer<OutputStream> getUsersReportFile(GetEventsReportRequest request) {
        return REPORT_SERVICES.get(request.db())
            .getUsersReportFile(request);
    }
}

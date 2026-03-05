package ru.khehelk.practice.service.report.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.khehelk.practice.domain.dto.UserReportRow;
import ru.khehelk.practice.domain.dto.request.GetEventsReportRequest;
import ru.khehelk.practice.domain.enums.Database;
import ru.khehelk.practice.export.file.FileExportConfig;
import ru.khehelk.practice.export.file.FileExportContext;
import ru.khehelk.practice.export.file.csv.CsvExportConfig;
import ru.khehelk.practice.export.file.xlsx.XlsxExportConfig;
import ru.khehelk.practice.helper.export.meta.UsersReportMeta;
import ru.khehelk.practice.repository.clickhouse.ChUsersReportRepository;
import ru.khehelk.practice.service.report.ReportService;

@Service
@RequiredArgsConstructor
public class ChReportService implements ReportService {

    private final ChUsersReportRepository chReportRepository;

    @Override
    public Database getDatabase() {
        return Database.CLICKHOUSE;
    }

    public Consumer<OutputStream> getUsersReportFile(GetEventsReportRequest request) {
        return outputStream -> {
            FileExportConfig<UserReportRow> config = switch (request.format()) {
                case CSV -> new CsvExportConfig<>();
                case XLSX -> XlsxExportConfig.<UserReportRow>builder()
                    .outputStream(outputStream)
                    .report(rowConsumer ->
                        chReportRepository.fetchStreamUsers(request.format().name(), context ->
                            rowConsumer.accept(context.getResultObject())))
                    .sheetName("Отчёт по пользователям")
                    .columnMetas(UsersReportMeta.CH_XLSX)
                    .build();
            };
            try {
                FileExportContext.export(config);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}

package ru.khehelk.practice.service.report.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.khehelk.practice.domain.dto.request.GetEventsReportRequest;
import ru.khehelk.practice.domain.enums.Database;
import ru.khehelk.practice.export.file.FileExportConfig;
import ru.khehelk.practice.export.file.FileExportContext;
import ru.khehelk.practice.export.file.csv.CsvExportConfig;
import ru.khehelk.practice.export.file.xlsx.XlsxExportConfig;
import ru.khehelk.practice.helper.export.meta.UsersReportMeta;
import ru.khehelk.practice.jooq.tables.pojos.Users;
import ru.khehelk.practice.repository.postgres.PgUsersReportRepository;
import ru.khehelk.practice.service.report.ReportService;

@Service
@RequiredArgsConstructor
public class PgReportService implements ReportService {

    private final PgUsersReportRepository pgReportRepository;

    @Override
    public Database getDatabase() {
        return Database.POSTGRES;
    }

    public Consumer<OutputStream> getUsersReportFile(GetEventsReportRequest request) {
        return outputStream -> {
            FileExportConfig<Users> config = switch (request.format()) {
                case CSV -> new CsvExportConfig<>();
                case XLSX -> XlsxExportConfig.<Users>builder()
                    .outputStream(outputStream)
                    .report(rowConsumer -> {
                        try(var cursor = pgReportRepository.streamUsersReport()) {
                            cursor.forEach(user -> {
                                Users mappedUser = user.into(Users.class);
                                rowConsumer.accept(mappedUser);
                            });
                        }
                    })
                    .sheetName("Отчёт по пользователям")
                    .columnMetas(UsersReportMeta.PG_XLSX)
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

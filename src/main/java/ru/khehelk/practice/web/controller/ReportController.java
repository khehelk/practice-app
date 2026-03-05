package ru.khehelk.practice.web.controller;

import java.io.OutputStream;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import ru.khehelk.practice.domain.dto.request.GetEventsReportRequest;
import ru.khehelk.practice.service.ReportServiceContext;
import ru.khehelk.practice.web.api.ReportApi;

@RestController
@RequiredArgsConstructor
public class ReportController implements ReportApi {

    private final ReportServiceContext reportServiceContext;

    @Override
    public ResponseEntity<StreamingResponseBody> getUsersReportFile(GetEventsReportRequest request) {
        Consumer<OutputStream> consumer = reportServiceContext.getUsersReportFile(request);
        StreamingResponseBody body = consumer::accept;
        String filename = "users_report." + request.format().getExtension();
        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
            .header("Content-Type", request.format().getContentType())
            .body(body);
    }
}

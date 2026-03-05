package ru.khehelk.practice.web.api;

import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import ru.khehelk.practice.domain.dto.request.GetEventsReportRequest;

@RequestMapping(ReportApi.BASE_MAPPING)
public interface ReportApi {
    String BASE_MAPPING = ApiConsts.API_V1 + "/report";

    @GetMapping("/users/file")
    ResponseEntity<StreamingResponseBody> getUsersReportFile(
        @Valid @ParameterObject GetEventsReportRequest request
    );
}

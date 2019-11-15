package org.nure.julia.web;

import org.nure.julia.HystrixFallbackController;
import org.nure.julia.dto.ReportExportDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;

import static org.nure.julia.web.WebControllerDefinitions.EXPORT_TO_CSV;

@FeignClient(name = "${spring.application.name}.ReportsController")
public interface ReportsController extends HystrixFallbackController {

    @PostMapping(EXPORT_TO_CSV)
    void exportDataToCsv(HttpServletResponse response, @RequestBody ReportExportDto reportExportDto);
}

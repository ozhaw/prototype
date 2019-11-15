package org.nure.julia.web.spi;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.nure.julia.dto.ReportExportDto;
import org.nure.julia.exceptions.CSVExportException;
import org.nure.julia.service.ReportExportService;
import org.nure.julia.web.ApplicationController;
import org.nure.julia.web.ReportsController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

import static org.nure.julia.web.WebControllerDefinitions.BASE_URL;

@ApplicationController
@RequestMapping(BASE_URL)
public class ReportsControllerImpl implements ReportsController {
    private final ReportExportService reportExportService;

    public ReportsControllerImpl(ReportExportService reportExportService) {
        this.reportExportService = reportExportService;
    }

    @Override
    @HystrixCommand(commandKey = "basic", fallbackMethod = "fallback", ignoreExceptions = {
            CSVExportException.class
    })
    public void exportDataToCsv(HttpServletResponse response, ReportExportDto reportExportDto) {
        reportExportService.exportToCsv(response, reportExportDto);
    }

    private void fallback(HttpServletResponse response, ReportExportDto reportExportDto) {
        this.defaultFallback();
    }

}

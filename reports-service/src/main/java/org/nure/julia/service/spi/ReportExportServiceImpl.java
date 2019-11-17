package org.nure.julia.service.spi;

import org.nure.julia.dto.ReportExportDto;
import org.nure.julia.exceptions.CSVExportException;
import org.nure.julia.service.ReportExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class ReportExportServiceImpl implements ReportExportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportExportServiceImpl.class);

    @Override
    public void exportToCsv(HttpServletResponse response, ReportExportDto reportExportDto) {
        String csvFileName = String.format("%s-%s.csv", reportExportDto.getTitle(), new Date().toString());

        response.setContentType("text/csv");
        response.setHeader("FileName", csvFileName);

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                csvFileName);
        response.setHeader(headerKey, headerValue);

        try {
            String sb = String.join(",", reportExportDto.getHeaders()) +
                    System.lineSeparator() +
                    reportExportDto.getData().stream()
                            .map(stack -> String.join(",", stack))
                            .collect(Collectors.joining(System.lineSeparator()));
            response.getWriter().print(sb);
        } catch (IOException e) {
            LOGGER.error("Unable to get Response writer", e);
            throw new CSVExportException("Cannot export to CSV file", e);
        }
    }

}

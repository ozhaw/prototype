package org.nure.julia.service.spi;

import org.nure.julia.dto.ReportExportDto;
import org.nure.julia.exceptions.CSVExportException;
import org.nure.julia.service.ReportExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Service
public class ReportExportServiceImpl implements ReportExportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportExportServiceImpl.class);

    @Override
    public void exportToCsv(HttpServletResponse response, ReportExportDto reportExportDto) {
        String csvFileName = String.format("%s-%s.csv", reportExportDto.getTitle(), new Date().toString());

        response.setContentType("text/csv");

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                csvFileName);
        response.setHeader(headerKey, headerValue);

        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE)) {
            csvWriter.writeHeader(reportExportDto.getHeaders());

            reportExportDto.getData().forEach(stack -> stack.forEach(item -> {
                try {
                    csvWriter.write(item, reportExportDto.getHeaders());
                } catch (IOException e) {
                    LOGGER.error("Unable to write data");
                }
            }));
        } catch (IOException e) {
            LOGGER.error("Unable to get Response writer", e);
            throw new CSVExportException("Cannot export to CSV file", e);
        }
    }

}

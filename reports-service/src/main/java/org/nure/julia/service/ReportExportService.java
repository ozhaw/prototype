package org.nure.julia.service;

import org.nure.julia.dto.ReportExportDto;

import javax.servlet.http.HttpServletResponse;

public interface ReportExportService {
    void exportToCsv(HttpServletResponse response, ReportExportDto reportExportDto);
}

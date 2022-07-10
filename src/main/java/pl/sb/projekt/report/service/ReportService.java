package pl.sb.projekt.report.service;

import pl.sb.projekt.report.dto.ProjectReportDto;
import pl.sb.projekt.report.dto.UserReportDto;
import pl.sb.projekt.report.search.DateRange;

import java.util.UUID;

public interface ReportService {

    UserReportDto getUserFullReport(final UUID managerUuid, final UUID userUuid, final DateRange dateRange);

    ProjectReportDto getProjectFullReport(final UUID managerUuid, final UUID projectUuid, final DateRange dateRange);
}

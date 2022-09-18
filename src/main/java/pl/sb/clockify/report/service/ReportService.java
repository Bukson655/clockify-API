package pl.sb.clockify.report.service;

import pl.sb.clockify.report.dto.ProjectReportDto;
import pl.sb.clockify.report.dto.UserReportDto;
import pl.sb.clockify.report.search.DateRange;

import java.util.UUID;

public interface ReportService {

    UserReportDto getUserFullReport(final UUID managerUuid, final UUID userUuid, final DateRange dateRange);

    ProjectReportDto getProjectFullReport(final UUID managerUuid, final UUID projectUuid, final DateRange dateRange);
}

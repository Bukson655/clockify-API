package pl.sb.clockify.report.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.sb.clockify.report.dto.ProjectReportDto;
import pl.sb.clockify.report.dto.UserReportDto;
import pl.sb.clockify.report.search.DateRange;
import pl.sb.clockify.report.service.ReportService;

import java.util.UUID;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{userUuid}")
    public UserReportDto getAllProjects(@PathVariable final UUID userUuid,
                                        @RequestParam final UUID managerUuid,
                                        @RequestParam final DateRange dateRange) {
        return reportService.getUserFullReport(managerUuid, userUuid, dateRange);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/projects/{projectUuid}")
    public ProjectReportDto getAllUsers(@PathVariable final UUID projectUuid,
                                        @RequestParam final UUID managerUuid,
                                        @RequestParam final DateRange dateRange) {
        return reportService.getProjectFullReport(managerUuid, projectUuid, dateRange);
    }

}
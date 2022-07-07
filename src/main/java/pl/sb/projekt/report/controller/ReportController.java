package pl.sb.projekt.report.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.sb.projekt.report.dto.ReportDto;
import pl.sb.projekt.report.search.DateRange;
import pl.sb.projekt.report.service.ProjectReportService;
import pl.sb.projekt.report.service.UserReportService;

import java.util.UUID;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final UserReportService userReportService;
    private final ProjectReportService projectReportService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{userUuid}")
    public ReportDto getAllProjects(@PathVariable final UUID userUuid,
                                    @RequestParam final UUID managerUuid,
                                    @RequestParam final DateRange dateRange) {
        return userReportService.getFullReport(managerUuid, userUuid, dateRange);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/projects/{projectUuid}")
    public ReportDto getAllUsers(@PathVariable final UUID projectUuid,
                                 @RequestParam final UUID managerUuid,
                                 @RequestParam final DateRange dateRange) {
        return projectReportService.getFullReport(managerUuid, projectUuid, dateRange);
    }

}
package pl.sb.projekt.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sb.projekt.common.exception.NotFoundException;
import pl.sb.projekt.project.model.Project;
import pl.sb.projekt.project.repository.ProjectRepository;
import pl.sb.projekt.record.model.Record;
import pl.sb.projekt.report.dto.ProjectReportDto;
import pl.sb.projekt.report.dto.ReportDto;
import pl.sb.projekt.report.dto.ReportDetails;
import pl.sb.projekt.report.search.DateRange;
import pl.sb.projekt.user.model.User;
import pl.sb.projekt.user.model.UserRole;
import pl.sb.projekt.user.service.UserService;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectReportService extends ReportService {

    private final UserService userService;
    private final ProjectRepository projectRepository;

    public ReportDto getFullReport(final UUID managerUuid, final UUID projectUuid, final DateRange dateRange) {
        userService.verifyRoleByUuid(managerUuid, UserRole.MANAGER);
        final LocalDateTime lowerDateRange = getLowerDateRange(dateRange);
        final Project project = projectRepository.findByUuidWithDateRange(projectUuid, lowerDateRange, LocalDate.now().atStartOfDay())
                .orElseThrow(() -> new NotFoundException(String.format("Project with UUID %s does not exist", projectUuid)));
        Set<Record> records = project.getRecords();
        final ReportDetails overallHoursAndCost = getOverallHoursAndCost(records);
        final BigDecimal overallCost = overallHoursAndCost.getCostDetail();
        final BigDecimal allHoursWorked = overallHoursAndCost.getWorkedHoursDetail();
        final boolean isOverBudget = verifyBudget(project);
        final Map<String, ReportDetails> userInProjects = getUsersHoursAndCostSummary(project);

        return new ProjectReportDto(isOverBudget, overallCost, allHoursWorked, userInProjects);
    }

    private boolean verifyBudget(final Project project) {
        return project.getBudgetUse().compareTo(BigDecimal.valueOf(100)) > 0;
    }

    private Map<String, ReportDetails> getUsersHoursAndCostSummary(final Project project) {
        final Map<String, ReportDetails> userInProjectDetails = new HashMap<>();

        for (User user : project.getUsers()) {
            project.getRecords().stream()
                    .filter(record -> record.getUser().getUuid().equals(user.getUuid()))
                    .map(rec -> {
                        BigDecimal hoursInProject = BigDecimal.valueOf(Duration.between(rec.getStartDateTime(), rec.getEndDateTime()).toHours());
                        BigDecimal costOfWork = rec.getCostOfWork();
                        return new ReportDetails(costOfWork, hoursInProject);
                    })
                    .forEach(reportDetails -> userInProjectDetails.put(user.getLogin(), reportDetails));
        }
        return userInProjectDetails;
    }

}
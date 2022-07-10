package pl.sb.projekt.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sb.projekt.common.exception.NotFoundException;
import pl.sb.projekt.project.repository.ProjectRepository;
import pl.sb.projekt.record.repository.RecordRepository;
import pl.sb.projekt.report.dto.ProjectReportDto;
import pl.sb.projekt.report.dto.ReportProjectProjection;
import pl.sb.projekt.report.dto.ReportDetails;
import pl.sb.projekt.report.dto.ReportUserProjection;
import pl.sb.projekt.report.dto.UserReportDto;
import pl.sb.projekt.report.search.DateRange;
import pl.sb.projekt.user.model.UserRole;
import pl.sb.projekt.user.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private static final BigDecimal HUNDRED_PERCENT = BigDecimal.valueOf(100);

    private final UserService userService;
    private final RecordRepository recordRepository;
    private final ProjectRepository projectRepository;

    public UserReportDto getUserFullReport(final UUID managerUuid, final UUID userUuid, final DateRange dateRange) {
        userService.verifyRoleByUuid(managerUuid, UserRole.MANAGER);
        final LocalDateTime lowerDateRange = getLowerDateRange(dateRange);
        List<ReportUserProjection> results = recordRepository.findUserSummaryByUuidAndDateRange(userUuid, lowerDateRange, LocalDate.now().atStartOfDay());

        final UserReportDto userReportDto = new UserReportDto();
        results.forEach(project -> calculateTotalForUser(project, userReportDto));
        if (!userReportDto.getUserInProjects().isEmpty()) {
            userReportDto.setCurrentCostPerHour(results.get(0).getCostPerHour());
        }
        return userReportDto;
    }

    public ProjectReportDto getProjectFullReport(final UUID managerUuid, final UUID projectUuid, final DateRange dateRange) {
        userService.verifyRoleByUuid(managerUuid, UserRole.MANAGER);
        final LocalDateTime lowerDateRange = getLowerDateRange(dateRange);
        List<ReportProjectProjection> results = recordRepository.findProjectSummaryByUuidAndDateRange(
                projectUuid, lowerDateRange, LocalDate.now().atStartOfDay());
        BigDecimal budgetUse = projectRepository.findBudgetUseByUuid(projectUuid)
                .orElseThrow(() -> new NotFoundException(String.format("Project with UUID %s does not exist", projectUuid)));
        final ProjectReportDto projectReportDto = new ProjectReportDto();
        results.forEach(users -> calculateTotalForProject(users, projectReportDto));
        setOverBudget(budgetUse, projectReportDto);
        return projectReportDto;
    }

    private void setOverBudget(BigDecimal budgetUse, ProjectReportDto projectReportDto) {
        boolean isOverBudget = budgetUse.compareTo(HUNDRED_PERCENT) > 0;
        projectReportDto.setIsOverBudget(isOverBudget);
    }

    private void calculateTotalForUser(final ReportUserProjection projection, final UserReportDto userReportDto) {
        userReportDto.setOverallCost(userReportDto.getOverallCost().add(projection.getSumCost()));
        userReportDto.setWorkedHours(userReportDto.getWorkedHours().add(projection.getSumHours()));
        ReportDetails reportDetails = new ReportDetails(projection.getTitle(), projection.getSumCost(), projection.getSumHours());
        userReportDto.getUserInProjects().add(reportDetails);
    }

    private void calculateTotalForProject(final ReportProjectProjection projection, final ProjectReportDto projectReportDto) {
        projectReportDto.setOverallCost(projectReportDto.getOverallCost().add(projection.getSumCost()));
        projectReportDto.setWorkedHours(projectReportDto.getWorkedHours().add(projection.getSumHours()));
        ReportDetails reportDetails = new ReportDetails(projection.getTitle(), projection.getSumCost(), projection.getSumHours());
        projectReportDto.getUserInProjects().add(reportDetails);
    }

    private LocalDateTime getLowerDateRange(final DateRange dateRange) {
        final LocalDateTime now = LocalDate.now().atStartOfDay();
        return switch (dateRange) {
            case TOTAL -> LocalDate.EPOCH.atStartOfDay();
            case YEAR -> now.minusYears(1);
            case MONTH -> now.minusMonths(1);
            case WEEK -> now.minusWeeks(1);
        };
    }

}
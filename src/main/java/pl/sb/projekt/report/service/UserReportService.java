package pl.sb.projekt.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sb.projekt.common.exception.NotFoundException;
import pl.sb.projekt.project.model.Project;
import pl.sb.projekt.record.model.Record;
import pl.sb.projekt.report.dto.ReportDto;
import pl.sb.projekt.report.dto.UserReportDto;
import pl.sb.projekt.report.search.DateRange;
import pl.sb.projekt.report.dto.ReportDetails;
import pl.sb.projekt.user.model.User;
import pl.sb.projekt.user.model.UserRole;
import pl.sb.projekt.user.repository.UserRepository;
import pl.sb.projekt.user.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserReportService extends ReportService {

    private final UserRepository userRepository;
    private final UserService userService;

    public ReportDto getFullReport(final UUID managerUuid, final UUID userUuid, final DateRange dateRange) {
        userService.verifyRoleByUuid(managerUuid, UserRole.MANAGER);
        final LocalDateTime lowerDateRange = getLowerDateRange(dateRange);
        final User user = userRepository.findByUuidWithDateRange(userUuid, lowerDateRange, LocalDate.now().atStartOfDay())
                .orElseThrow(() -> new NotFoundException(String.format("User with UUID %s does not exist", userUuid)));
        Set<Record> records = user.getRecords();
        final Map<String, ReportDetails> userInProjects = getProjectHoursAndCostSummary(user);
        final BigDecimal overallCost = getOverallCost(records);
        final BigDecimal allHoursWorked = getAllHoursWorked(records);
        return new UserReportDto(user.getCostPerHour(), overallCost, allHoursWorked, userInProjects);
    }

    private Map<String, ReportDetails> getProjectHoursAndCostSummary(final User user) {
        final Map<String, ReportDetails> userInProjectDetails = new HashMap<>();

        for (Project project : user.getProjects()) {
            final Set<Record> records = user.getRecords();
            final List<Record> filteredRecords = records.stream()
                    .filter(record -> record.getProject().getUuid().equals(project.getUuid()))
                    .toList();
            final BigDecimal overallCostForProject = calculateOverallCost(filteredRecords);
            final BigDecimal workedHoursInProject = calculateWorkedHours(filteredRecords);
            userInProjectDetails.put(project.getTitle(),
                    new ReportDetails(overallCostForProject, workedHoursInProject));
        }
        return userInProjectDetails;
    }

}
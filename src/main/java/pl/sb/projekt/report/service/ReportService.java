package pl.sb.projekt.report.service;

import pl.sb.projekt.record.model.Record;
import pl.sb.projekt.report.dto.ReportDetails;
import pl.sb.projekt.report.dto.ReportDto;
import pl.sb.projekt.report.search.DateRange;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

abstract class ReportService {

    public abstract ReportDto getFullReport(final UUID managerUuid, final UUID objectUuid, final DateRange dateRange);

    LocalDateTime getLowerDateRange(final DateRange dateRange) {
        final LocalDateTime now = LocalDate.now().atStartOfDay();
        return switch (dateRange) {
            case TOTAL -> LocalDate.EPOCH.atStartOfDay();
            case YEAR -> now.minusYears(1);
            case MONTH -> now.minusMonths(1);
            case WEEK -> now.minusWeeks(1);
        };
    }

    ReportDetails getOverallHoursAndCost(final Set<Record> records) {
        ReportDetails reportDetails = new ReportDetails();
        records.stream()
                .map(rec -> {
                    BigDecimal hoursInProject = BigDecimal.valueOf(Duration.between(rec.getStartDateTime(), rec.getEndDateTime()).toHours());
                    BigDecimal costOfWork = rec.getCostOfWork();
                    return new ReportDetails(costOfWork, hoursInProject);
                })
                .forEach(obj -> {
                    reportDetails.setCostDetail(reportDetails.getCostDetail().add(obj.getCostDetail()));
                    reportDetails.setWorkedHoursDetail(reportDetails.getWorkedHoursDetail().add(obj.getWorkedHoursDetail()));
                });
        return reportDetails;
    }

}
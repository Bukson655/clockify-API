package pl.sb.projekt.report.service;

import pl.sb.projekt.record.model.Record;
import pl.sb.projekt.report.dto.ReportDto;
import pl.sb.projekt.report.search.DateRange;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

    BigDecimal getOverallCost(final Set<Record> records) {
        return records.stream()
                .map(Record::getCostOfWork)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    BigDecimal getAllHoursWorked(final Set<Record> records) {
        long allHoursWorked = records.stream()
                .map(record -> Duration.between(record.getStartDateTime(), record.getEndDateTime()).toHours())
                .mapToLong(Long::longValue).sum();
        return BigDecimal.valueOf(allHoursWorked);
    }

    BigDecimal calculateWorkedHours(final List<Record> filteredRecords) {
        return filteredRecords.stream()
                .map(rec -> Duration.between(rec.getStartDateTime(), rec.getEndDateTime()).toHours())
                .map(BigDecimal::new)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    BigDecimal calculateOverallCost(final List<Record> filteredRecords) {
        return filteredRecords.stream()
                .map(Record::getCostOfWork)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
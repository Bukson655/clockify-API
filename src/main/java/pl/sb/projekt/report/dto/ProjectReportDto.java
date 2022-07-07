package pl.sb.projekt.report.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class ProjectReportDto extends ReportDto {

    private boolean isOverBudget;

    public ProjectReportDto(boolean isOverBudget, BigDecimal overallCost, BigDecimal workedHours, Map<String, ReportDetails> userInProjects) {
        super(overallCost, workedHours, userInProjects);
        this.isOverBudget = isOverBudget;
    }
}

package pl.sb.projekt.report.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class UserReportDto extends ReportDto {

    private BigDecimal currentCostPerHour;

    public UserReportDto(BigDecimal currentCostPerHour, BigDecimal overallCost, BigDecimal workedHours,
                         Map<String, ReportDetails> userInProjects) {
        super(overallCost, workedHours, userInProjects);
        this.currentCostPerHour = currentCostPerHour;
    }
}
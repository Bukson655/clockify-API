package pl.sb.projekt.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public abstract class ReportDto {

    private BigDecimal overallCost;
    private BigDecimal workedHours;
    private Map<String, ReportDetails> userInProjects;

}

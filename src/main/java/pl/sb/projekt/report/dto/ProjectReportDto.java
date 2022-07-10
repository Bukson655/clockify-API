package pl.sb.projekt.report.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProjectReportDto {

    private BigDecimal overallCost = BigDecimal.ZERO;
    private BigDecimal workedHours = BigDecimal.ZERO;
    private List<ReportDetails> userInProjects = new ArrayList<>();
    private Boolean isOverBudget;
}

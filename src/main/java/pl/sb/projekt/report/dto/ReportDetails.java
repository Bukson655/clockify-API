package pl.sb.projekt.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
public class ReportDetails {

    private String name;
    private BigDecimal overallCost;
    private BigDecimal workedHours;
}

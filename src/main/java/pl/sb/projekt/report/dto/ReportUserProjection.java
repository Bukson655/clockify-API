package pl.sb.projekt.report.dto;

import java.math.BigDecimal;

public interface ReportUserProjection {

    String getTitle();

    BigDecimal getCostPerHour();

    BigDecimal getSumCost();

    BigDecimal getSumHours();

}

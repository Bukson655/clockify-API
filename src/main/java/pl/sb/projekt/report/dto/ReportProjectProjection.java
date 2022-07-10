package pl.sb.projekt.report.dto;

import java.math.BigDecimal;

public interface ReportProjectProjection {

    String getTitle();

    BigDecimal getSumCost();

    BigDecimal getSumHours();

}

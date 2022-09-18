package pl.sb.clockify.report.dto;

import java.math.BigDecimal;

public interface ReportProjectProjection {

    String getTitle();

    BigDecimal getSumCost();

    BigDecimal getSumHours();

}

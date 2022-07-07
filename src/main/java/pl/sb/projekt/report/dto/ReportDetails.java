package pl.sb.projekt.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDetails {

    private BigDecimal costDetail = BigDecimal.ZERO;
    private BigDecimal workedHoursDetail = BigDecimal.ZERO;
}

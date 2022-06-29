package pl.sb.projekt.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.sb.projekt.user.dto.UserDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ProjectDto {

    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal budget;
    private BigDecimal currentSpending;
    private BigDecimal budgetUse;
    private Set<UserDto> users;

}
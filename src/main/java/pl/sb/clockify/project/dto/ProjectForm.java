package pl.sb.clockify.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectForm {

    @NotBlank(message = "title cannot be blank")
    @Size(max = 50)
    private String title;

    @Nullable
    @Size(max = 255)
    private String description;

    @NotNull(message = "endDate cannot be null, use pattern : yyyy-MM-mm")
    @DateTimeFormat(pattern = "yyyy-MM-mm")
    private LocalDate startDate;

    @NotNull(message = "endDate cannot be null, use pattern : yyyy-MM-mm")
    @DateTimeFormat(pattern = "yyyy-MM-mm")
    private LocalDate endDate;

    @NotNull
    @Positive(message = "budget has to be greater than 0")
    private BigDecimal budget;

}
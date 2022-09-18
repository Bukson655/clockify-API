package pl.sb.clockify.record.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class RecordForm {

    private static final long MINUTES_IN_HOUR = 60;
    private static final int DIVIDE_SCALE = 5;

    @NotNull(message = "startDateTime cannot be null, use pattern : yyyy-MM-mm hh:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime startDateTime;

    @NotNull(message = "endDateTime cannot be null, use pattern : yyyy-MM-mm hh:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime endDateTime;

    @Nullable
    @Size(max = 255)
    private String description;

    @NotNull(message = "projectUuid cannot be null")
    private UUID projectUuid;

    @NotNull(message = "userUuid cannot be null")
    private UUID userUuid;

    public BigDecimal getTimeInMinutes() {
        return BigDecimal.valueOf(Duration.between(startDateTime, endDateTime).toMinutes());
    }

}
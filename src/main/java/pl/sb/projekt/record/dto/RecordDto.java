package pl.sb.projekt.record.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RecordDto {

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private String projectName;

    private String description;
}
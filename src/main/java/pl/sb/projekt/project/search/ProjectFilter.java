package pl.sb.projekt.project.search;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProjectFilter {

    private String title;
    private LocalDate startFrom;
    private LocalDate endTo;
    private boolean overBudget;
    private List<UUID> uuids;

}
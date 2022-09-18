package pl.sb.clockify.project.service;

import pl.sb.clockify.project.dto.ProjectDto;
import pl.sb.clockify.project.dto.ProjectForm;
import pl.sb.clockify.project.model.Project;
import pl.sb.clockify.project.search.ProjectFilter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProjectService {

    List<ProjectDto> getAllProjects(final UUID managerUuid);

    void deleteProjectByUuid(final UUID uuid, final UUID managerUuid);

    void saveProject(final ProjectForm projectForm, final UUID adminUuid);

    ProjectForm updateProject(final UUID uuid, final ProjectForm projectForm, final UUID managerUuid);

    void allocateToProject(final UUID projectUuid, final UUID managerUuid, final UUID userUuid);

    void removeFromProject(final UUID projectUuid, final UUID managerUuid, final UUID userUuid);

    List<ProjectDto> getFilteredProjects(final ProjectFilter projectFilter, final UUID managerUuid);

    BigDecimal countCurrentSpendingForProject(final Project project);

    void updateBudgetUse(Project project, BigDecimal currentSpending);
}


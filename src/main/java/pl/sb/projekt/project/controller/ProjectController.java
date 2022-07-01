package pl.sb.projekt.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.sb.projekt.project.dto.ProjectDto;
import pl.sb.projekt.project.dto.ProjectForm;
import pl.sb.projekt.project.search.ProjectFilter;
import pl.sb.projekt.project.service.ProjectService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ProjectDto> getAllProjects(@RequestParam final UUID managerUuid) {
        return projectService.getAllProjects(managerUuid);
    }

    @PostMapping("/{projectUuid}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void allocateUserToProject(@PathVariable final UUID projectUuid,
                                      @RequestParam final UUID userUuid,
                                      @RequestParam final UUID managerUuid) {
        projectService.allocateToProject(projectUuid, managerUuid, userUuid);
    }

    @DeleteMapping("/{projectUuid}/{userUuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUserFromProject(@PathVariable final UUID projectUuid,
                                      @PathVariable final UUID userUuid,
                                      @RequestParam final UUID managerUuid) {
        projectService.removeFromProject(projectUuid, managerUuid, userUuid);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProjectForm createProject(@RequestBody @Valid final ProjectForm projectForm,
                                     @RequestParam("uuid") final UUID managerUuid) {
        projectService.saveProject(projectForm, managerUuid);
        return projectForm;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{projectUuid}")
    public void deleteProject(@PathVariable final UUID projectUuid,
                              @RequestParam(name = "uuid") final UUID managerUuid) {
        projectService.deleteProjectByUuid(projectUuid, managerUuid);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{uuid}")
    public ProjectForm updateProject(@PathVariable final UUID uuid,
                                     @RequestBody @Valid final ProjectForm projectForm,
                                     @RequestParam(name = "uuid") final UUID managerUuid) {
        return projectService.updateProject(uuid, projectForm, managerUuid);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/filter")
    public List<ProjectDto> getFilteredProjects(@RequestBody @Valid final ProjectFilter projectFilter,
                                                @RequestParam(name = "uuid") final UUID managerUuid) {
        return projectService.getFilteredProjects(projectFilter, managerUuid);
    }

}
package pl.sb.projekt.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sb.projekt.common.exception.NotFoundException;
import pl.sb.projekt.project.dto.ProjectDto;
import pl.sb.projekt.project.dto.ProjectForm;
import pl.sb.projekt.project.mapper.ProjectMapper;
import pl.sb.projekt.project.model.Project;
import pl.sb.projekt.project.repository.ProjectRepository;
import pl.sb.projekt.project.search.ProjectFilter;
import pl.sb.projekt.project.search.ProjectSpecification;
import pl.sb.projekt.record.model.Record;
import pl.sb.projekt.user.model.User;
import pl.sb.projekt.user.model.UserRole;
import pl.sb.projekt.user.repository.UserRepository;
import pl.sb.projekt.user.service.UserService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public List<ProjectDto> getAllProjects(final UUID managerUuid) {
        userService.verifyRoleByUuid(managerUuid, UserRole.MANAGER);
        return projectRepository.findAllProjectsWithUsers().stream()
                .map(project -> {
                    BigDecimal currentSpending = countCurrentSpendingForProject(project);
                    return ProjectMapper.convertToDto(project, currentSpending);
                })
                .toList();
    }

    @Transactional
    public void allocateToProject(final UUID projectUuid, final UUID managerUuid, final UUID userUuid) {
        final Project project = getProject(projectUuid, managerUuid);
        final User user = userRepository.findByUuid(userUuid)
                .orElseThrow(() -> new NotFoundException(String.format("User with UUID %s does not exist", userUuid)));
        project.addUser(user);
    }

    @Transactional
    public void removeFromProject(final UUID projectUuid, final UUID managerUuid, final UUID userUuid) {
        final Project project = getProject(projectUuid, managerUuid);
        final User user = userRepository.findByUuid(userUuid)
                .orElseThrow(() -> new NotFoundException(String.format("User with UUID %s does not exist", userUuid)));
        project.removeUser(user);
    }

    @Transactional
    public void deleteProjectByUuid(final UUID uuid, final UUID managerUuid) {
        userService.verifyRoleByUuid(managerUuid, UserRole.MANAGER);
        projectRepository.deleteByUuid(uuid);
    }

    @Transactional
    public void saveProject(final ProjectForm projectForm, final UUID managerUuid) {
        userService.verifyRoleByUuid(managerUuid, UserRole.MANAGER);
        verifyTitle(projectForm);
        final Project project = ProjectMapper.convertFromForm(projectForm);
        projectRepository.save(project);
    }

    @Transactional
    public ProjectForm updateProject(final UUID uuid, final ProjectForm projectForm, final UUID managerUuid) {
        userService.verifyRoleByUuid(managerUuid, UserRole.MANAGER);
        Project project = verifyTitleFromExistingProject(uuid, projectForm);
        return ProjectMapper.convertToForm(ProjectMapper.setProjectFields(projectForm, project));
    }

    public List<ProjectDto> getFilteredProjects(final ProjectFilter projectFilter, final UUID managerUuid) {
        userService.verifyRoleByUuid(managerUuid, UserRole.MANAGER);
        final ProjectSpecification projectSpecification = new ProjectSpecification(projectFilter);

        return projectRepository.findAll(projectSpecification)
                .stream()
                .map(project -> ProjectMapper.convertToDto(project,
                        countCurrentSpendingForProject(project)))
                .collect(Collectors.toList());
    }

    private Project getProject(final UUID projectUuid, final UUID managerUuid) {
        final Project project = projectRepository.findByUuid(projectUuid)
                .orElseThrow(() -> new NotFoundException(String.format("Project with UUID %s does not exist", projectUuid)));
        userService.verifyRoleByUuid(managerUuid, UserRole.MANAGER);
        return project;
    }

    private void verifyTitle(final ProjectForm projectForm) {
        if (projectRepository.existsByTitle(projectForm.getTitle())) {
            throw new DataIntegrityViolationException(
                    String.format("Project title %s is already in use", projectForm.getTitle()));
        }
    }

    private Project verifyTitleFromExistingProject(final UUID uuid, final ProjectForm projectForm) {
        Project entity = projectRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException(String.format("Project with UUID %s does not exist", uuid)));
        if (projectRepository.existsByTitleAndTitleNot(projectForm.getTitle(), entity.getTitle())) {
            throw new DataIntegrityViolationException(
                    String.format("Project title %s is already in use", projectForm.getTitle()));
        }
        return entity;
    }

    public BigDecimal countCurrentSpendingForProject(final Project project) {
        BigDecimal currentSpending = BigDecimal.ZERO;
        for (final Record record : project.getRecords()) {
            final BigDecimal costOfWork = record.getCostOfWork();
            currentSpending = currentSpending.add(costOfWork);
        }
        return currentSpending;
    }

    public void updateBudgetUse(final Project project, final BigDecimal currentSpending) {
        project.setBudgetUse(currentSpending
                .multiply(BigDecimal.valueOf(100))
                .divide(project.getBudget(), RoundingMode.DOWN));
    }

}
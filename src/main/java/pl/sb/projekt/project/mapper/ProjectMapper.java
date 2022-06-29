package pl.sb.projekt.project.mapper;

import pl.sb.projekt.project.dto.ProjectDto;
import pl.sb.projekt.project.dto.ProjectForm;
import pl.sb.projekt.project.model.Project;
import pl.sb.projekt.user.dto.UserDto;
import pl.sb.projekt.user.mapper.UserMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectMapper {

    public static ProjectForm convertToForm(final Project project) {
        final ProjectForm projectForm = new ProjectForm();
        projectForm.setTitle(project.getTitle());
        projectForm.setDescription(project.getDescription());
        projectForm.setStartDate(project.getStartDate());
        projectForm.setEndDate(project.getEndDate());
        projectForm.setCurrentSpending(project.getCurrentSpending());
        projectForm.setBudget(project.getBudget());
        return projectForm;
    }

    public static ProjectDto convertToDto(final Project project) {
        final ProjectDto projectDto = new ProjectDto();
        projectDto.setTitle(project.getTitle());
        projectDto.setDescription(project.getDescription());
        projectDto.setStartDate(project.getStartDate());
        projectDto.setEndDate(project.getEndDate());
        projectDto.setCurrentSpending(project.getCurrentSpending());
        projectDto.setBudget(project.getBudget());
        Set<UserDto> usersDto = project.getUsers()
                .stream()
                .map(UserMapper::convertToDto)
                .collect(Collectors.toSet());
        projectDto.setUsers(usersDto);
        projectDto.setBudgetUse(project.getCurrentSpending()
                .multiply(BigDecimal.valueOf(100))
                .divide(project.getBudget(), RoundingMode.DOWN));
        return projectDto;
    }

    public static Project convertFromForm(final ProjectForm projectForm) {
        final Project project = new Project();
        project.setTitle(projectForm.getTitle());
        project.setDescription(projectForm.getDescription());
        project.setStartDate(projectForm.getStartDate());
        project.setEndDate(projectForm.getEndDate());
        project.setCurrentSpending(projectForm.getCurrentSpending());
        project.setBudget(projectForm.getBudget());
        return project;
    }

    public static Project setProjectFields(final ProjectForm projectForm, final Project entity) {
        entity.setTitle(projectForm.getTitle());
        entity.setDescription(projectForm.getDescription());
        entity.setStartDate(projectForm.getStartDate());
        entity.setEndDate(projectForm.getEndDate());
        entity.setCurrentSpending(projectForm.getCurrentSpending());
        entity.setBudget(projectForm.getBudget());
        return entity;
    }
}

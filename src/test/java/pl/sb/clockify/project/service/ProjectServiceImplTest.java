package pl.sb.clockify.project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sb.clockify.project.model.Project;
import pl.sb.clockify.project.repository.ProjectRepository;
import pl.sb.clockify.user.model.User;
import pl.sb.clockify.user.model.UserRole;
import pl.sb.clockify.user.repository.UserRepository;
import pl.sb.clockify.user.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private ProjectServiceImpl projectService;
    private User userManager;
    private User userRegular;
    private Project project;
    private Set<User> users;

    @BeforeEach
    void init() {
        userManager = User.builder().login("managerSlawek").firstName("Sławomir").lastName("Błaszkiewicz").userRole(UserRole.ADMIN)
                .email("blaszkiewiczslawomir@gmail.com").password("admin123").costPerHour(BigDecimal.valueOf(100)).build();
        userRegular = User.builder().login("andrzej").firstName("Andrzej").lastName("Andrzejewski").userRole(UserRole.USER)
                .email("andrzejewski@wp.pl").password("andrzej123").costPerHour(BigDecimal.valueOf(110)).build();
        users = new HashSet<>();
        users.add(userManager);
        project = Project.builder().title("Test project").description("No needed").startDate(LocalDate.of(2022, 1, 10))
                .endDate(LocalDate.of(2022, 10, 29)).budget(BigDecimal.valueOf(100000)).users(users).build();
    }

    @Test
    @DisplayName("Should allocate user in project when given data is proper")
    void shouldAllocateUserInProjectWhenGivenDataIsProper() {
        //given
        final UUID managerUuid = userManager.getUuid();
        final UUID userUuid = userRegular.getUuid();
        final UUID projectUUid = project.getUuid();
        when(userRepository.findByUuid(userUuid)).thenReturn(Optional.ofNullable(userRegular));
        when(projectRepository.findByUuid(projectUUid)).thenReturn(Optional.ofNullable(project));
        doNothing().when(userService).verifyRoleByUuid(managerUuid, UserRole.MANAGER);

        //when
        projectService.allocateToProject(projectUUid, managerUuid, userUuid);

        //then
        assertThat(users).hasSize(2);
        assertThat(users).contains(userRegular);
    }

    @Test
    @DisplayName("Should remove user from project when given data is proper")
    void shouldRemoveUserFromProjectWhenGivenDataIsProper() {
        //given
        final UUID managerUuid = userManager.getUuid();
        final UUID projectUUid = project.getUuid();
        when(userRepository.findByUuid(managerUuid)).thenReturn(Optional.ofNullable(userManager));
        when(projectRepository.findByUuid(projectUUid)).thenReturn(Optional.ofNullable(project));
        doNothing().when(userService).verifyRoleByUuid(managerUuid, UserRole.MANAGER);

        //when
        projectService.removeFromProject(projectUUid, managerUuid, managerUuid);

        //then
        assertThat(users).isEmpty();
    }

}
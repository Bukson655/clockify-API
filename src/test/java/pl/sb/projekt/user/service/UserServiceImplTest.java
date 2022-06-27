package pl.sb.projekt.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import pl.sb.projekt.exception.NotFoundException;
import pl.sb.projekt.user.dto.UserDto;
import pl.sb.projekt.user.dto.UserForm;
import pl.sb.projekt.user.model.User;
import pl.sb.projekt.user.model.UserRole;
import pl.sb.projekt.user.repository.UserRepository;
import pl.sb.projekt.user.search.UserFilter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;
    private User userAdmin;
    private User userRegular;
    private UserForm userForm;

    @BeforeEach
    void init() {
        userAdmin = User.builder().id(1L).login("admin").firstName("Sławomir").lastName("Błaszkiewicz").userRole(UserRole.ADMIN)
                .email("blaszkiewiczslawomir@gmail.com").password("admin123").costPerHour(BigDecimal.valueOf(100)).build();
        userRegular = User.builder().id(2L).login("andrzej").firstName("Andrzej").lastName("Andrzejewski").userRole(UserRole.USER)
                .email("andrzejewski@wp.pl").password("andrzej123").costPerHour(BigDecimal.valueOf(110)).build();
        userForm = UserForm.builder().login("piter").firstName("Piotr").lastName("Frączewski").userRole(UserRole.USER)
                .email( "fraczewski@wp.pl").costPerHour(BigDecimal.valueOf(90)).build();
    }

    @Nested
    @DisplayName("GET ALL USERS")
    class getAllUsers {

        @Test
        @DisplayName("Should return all users")
        void shouldReturnAllUsers() {
            //given
            final List<User> users = List.of(userAdmin, userRegular);
            when(userRepository.findAll()).thenReturn(users);

            //when
            final List<UserDto> result = userService.getAllUsers();

            //then
            assertThat(result.size()).isEqualTo(2);
            assertThat(result.get(0)).usingRecursiveComparison()
                    .ignoringFields(User.Fields.id, User.Fields.uuid)
                    .isEqualTo(users.get(0));
        }
    }

    @Nested
    @DisplayName("GET BY UUID")
    class getUserByUuid {

        @Test
        @DisplayName("Should return existing user with given UUID")
        void shouldReturnExistingUserWithGivenUuid() {
            //given
            when(userRepository.findUserByUuid(userRegular.getUuid())).thenReturn(Optional.of(userRegular));

            //when
            final UserDto actualUser = userService.getUserByUuid(userRegular.getUuid());

            //then
            assertThat(actualUser).usingRecursiveComparison().isEqualTo(userRegular);
        }

        @Test
        @DisplayName("Should throw exception when user with given UUID does not exist")
        void shouldThrowExceptionWhenUserWithGivenUuidDoesNotExist() {
            //given
            final UUID randomUUID = UUID.randomUUID();
            when(userRepository.findUserByUuid(randomUUID)).thenReturn(Optional.empty());

            //when //then
            assertThatThrownBy(() -> userService.getUserByUuid(randomUUID))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(String.format("User with UUID %s does not exist", randomUUID));
        }
    }

    @Nested
    @DisplayName("DELETE BY UUID")
    class deleteUSer {

        @Test
        @DisplayName("Should delete user when given admin UUID is proper")
        void shouldDeleteUserWhenGivenAdminUuidIsProper() {
            //given
            final UUID uuidOfUserToBeDeleted = userRegular.getUuid();
            final UUID adminUuid = userAdmin.getUuid();
            when(userRepository.findUserByUuid(adminUuid)).thenReturn(Optional.ofNullable(userAdmin));

            //when
            userService.deleteUserByUuid(uuidOfUserToBeDeleted, adminUuid);

            //then
            verify(userRepository).deleteByUuid(uuidOfUserToBeDeleted);
        }

        @Test
        @DisplayName("Should not delete user with non existing admin UUID")
        void shouldNotDeleteUserWithNonExistingAdminUuid() {
            //given
            final UUID uuid = userRegular.getUuid();
            final UUID nonExistingUuid = UUID.randomUUID();

            //when // then
            assertThatThrownBy(() -> userService.deleteUserByUuid(uuid, nonExistingUuid))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(String.format("User with UUID %s does not exist", nonExistingUuid));
        }

        @Test
        @DisplayName("Should not delete user by UUID with no admin UUID")
        void shouldNotDeleteUserByUuidWithNoAdminUuid() {
            //given
            final UUID uuid = userRegular.getUuid();
            final UUID regularUserUuid = UUID.randomUUID();
            when(userRepository.findUserByUuid(regularUserUuid)).thenReturn(Optional.ofNullable(userRegular));

            //when // then
            assertThatThrownBy(() -> userService.deleteUserByUuid(uuid, regularUserUuid))
                    .isInstanceOf(SecurityException.class)
                    .hasMessage(String.format("Given UUID %s does not belong to admin", regularUserUuid));
        }
    }

    @Nested
    @DisplayName("SAVE USER")
    class saveUser {

        @Test
        @DisplayName("Should save user when given admin UUID is proper")
        void shouldSaveUserWhenGivenAdminUuidIsProper() {
            //given
            final UUID adminUuid = userAdmin.getUuid();
            UserForm userToSave = new UserForm("piter", "Piotr", "Frączewski", UserRole.USER,
                    "piter123", "f.piotr@wp.pl", BigDecimal.valueOf(95));
            when(userRepository.findUserByUuid(adminUuid)).thenReturn(Optional.ofNullable(userAdmin));
            when(userRepository.existsByLogin(userToSave.getLogin())).thenReturn(false);
            when(userRepository.existsByEmail(userToSave.getEmail())).thenReturn(false);

            //when
            userService.saveUser(userToSave, adminUuid);

            //then
            verify(userRepository).save(userArgumentCaptor.capture());
            final User capturedUser = userArgumentCaptor.getValue();
            assertThat(capturedUser).usingRecursiveComparison()
                    .ignoringFields(User.Fields.id, User.Fields.uuid)
                    .isEqualTo(userToSave);
        }

        @Test
        @DisplayName("Should not save user when given UUID is not exists")
        void shouldNotSaveUserWhenGivenUuidNotExists() {
            //given
            final UUID nonExistingUuid = UUID.randomUUID();
            when(userRepository.findUserByUuid(nonExistingUuid)).thenReturn(Optional.empty());

            //when then
            assertThatThrownBy(() -> userService.saveUser(userForm, nonExistingUuid))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(String.format("User with UUID %s does not exist", nonExistingUuid));
        }

        @Test
        @DisplayName("Should not save user when given UUID does not belong to admin")
        void shouldNotSaveUserWhenGivenUuidDoesNotBelongToAdmin() {
            //given
            final UUID regularUserUuid = userRegular.getUuid();
            when(userRepository.findUserByUuid(regularUserUuid)).thenReturn(Optional.ofNullable(userRegular));

            //when //then
            assertThatThrownBy(() -> userService.saveUser(userForm, regularUserUuid))
                    .isInstanceOf(SecurityException.class)
                    .hasMessage(String.format("Given UUID %s does not belong to admin", regularUserUuid));
        }

        @Test
        @DisplayName("Should not save user when login already exists")
        void shouldNotSaveUserWhenLoginAlreadyExists() {
            //given
            final UUID adminUuid = userAdmin.getUuid();
            when(userRepository.findUserByUuid(adminUuid)).thenReturn(Optional.ofNullable(userAdmin));
            when(userRepository.existsByLogin(userForm.getLogin())).thenReturn(true);

            //when //then
            assertThatThrownBy(() -> userService.saveUser(userForm, adminUuid))
                    .isInstanceOf(DataIntegrityViolationException.class)
                    .hasMessage(String.format("Login %s is already in use", userForm.getLogin()));
        }

        @Test
        @DisplayName("Should not save user when email already exists")
        void shouldNotSaveUserWhenEmailAlreadyExists() {
            //given
            final UUID adminUuid = userAdmin.getUuid();
            when(userRepository.findUserByUuid(adminUuid)).thenReturn(Optional.ofNullable(userAdmin));
            when(userRepository.existsByLogin(userForm.getLogin())).thenReturn(false);
            when(userRepository.existsByEmail(userForm.getEmail())).thenReturn(true);

            //when //then
            assertThatThrownBy(() -> userService.saveUser(userForm, adminUuid))
                    .isInstanceOf(DataIntegrityViolationException.class)
                    .hasMessage(String.format("Email %s is already in use", userForm.getEmail()));
        }
    }

    @Nested
    @DisplayName("UPDATE USER")
    class updateUser {

        @Test
        @DisplayName("Should update all user fields")
        void shouldUpdateAllUserFields() {
            //given
            final UserForm newUserData = new UserForm("danielegg", "Daniel", "Kanclerz", UserRole.USER,
                    "danieleg123", "kanclerz.daniel@wp.pl", BigDecimal.valueOf(100));
            final UUID adminUuid = userAdmin.getUuid();
            final UUID userToEditUuid = userRegular.getUuid();
            uuidSettings(adminUuid, userToEditUuid);
            when(userRepository.existsByLoginAndLoginNot(newUserData.getLogin(), userRegular.getLogin())).thenReturn(false);
            when(userRepository.existsByEmailAndEmailNot(newUserData.getEmail(), userRegular.getEmail())).thenReturn(false);

            //when
            final UserForm result = userService.updateUser(userToEditUuid, newUserData, adminUuid);

            //then
            assertThat(result).usingRecursiveComparison().isEqualTo(newUserData);
        }

        @Test
        @DisplayName("Should update some user fields")
        void shouldUpdateSomeUserFields() {
            //given
            final UserForm newUserData = new UserForm("piter", "Piotr", "Frąckiewicz", UserRole.USER,
                    "piotrek123", "frackiewicz.piotr@wp.pl", BigDecimal.valueOf(90));
            final UUID adminUuid = userAdmin.getUuid();
            final UUID userToEditUuid = userRegular.getUuid();
            uuidSettings(adminUuid, userToEditUuid);
            when(userRepository.existsByLoginAndLoginNot(newUserData.getLogin(), userRegular.getLogin())).thenReturn(false);
            when(userRepository.existsByEmailAndEmailNot(newUserData.getEmail(), userRegular.getEmail())).thenReturn(false);

            //when
            final UserForm result = userService.updateUser(userToEditUuid, newUserData, adminUuid);

            //then
            assertThat(result).usingRecursiveComparison().isEqualTo(newUserData);
        }

        @Test
        @DisplayName("Should not update when UUID does not belong to admin")
        void shouldNotUpdateWhenUuidDoesNotBelongToAdmin() {
            //given
            final UserForm newUserData = new UserForm("piter", "Piotr", "Frąckiewicz", UserRole.USER,
                    "piotrek123", "frackiewicz.piotr@wp.pl", BigDecimal.valueOf(90));
            final UUID nonAdminUuid = userRegular.getUuid();
            final UUID userToEditUuid = userRegular.getUuid();
            when(userRepository.findUserByUuid(nonAdminUuid)).thenReturn(Optional.ofNullable(userRegular));
            when(userRepository.findUserByUuid(userToEditUuid)).thenReturn(Optional.ofNullable(userRegular));

            //when //then
            assertThatThrownBy(() -> userService.updateUser(userToEditUuid, newUserData, nonAdminUuid))
                    .isInstanceOf(SecurityException.class)
                    .hasMessage(String.format("Given UUID %s does not belong to admin", nonAdminUuid));
        }

        @Test
        @DisplayName("Should not update when UUID does not exist")
        void shouldNotUpdateWhenUuidDoesNotExist() {
            //given
            final UserForm newUserData = new UserForm("piter", "Piotr", "Frąckiewicz", UserRole.USER,
                    "piotrek123", "frackiewicz.piotr@wp.pl", BigDecimal.valueOf(90));
            final UUID nonExistingUuid = UUID.randomUUID();
            final UUID userToEditUuid = userRegular.getUuid();
            when(userRepository.findUserByUuid(nonExistingUuid)).thenReturn(Optional.empty());

            //when //then
            assertThatThrownBy(() -> userService.updateUser(userToEditUuid, newUserData, nonExistingUuid))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(String.format("User with UUID %s does not exist", nonExistingUuid));
        }

        @Test
        @DisplayName("Should not update when login already exists")
        void shouldNotUpdateWhenLoginAlreadyExists() {
            //given
            final UserForm newUserData = new UserForm("piter", "Piotr", "Frąckiewicz", UserRole.USER,
                    "piotrek123", "frackiewicz.piotr@wp.pl", BigDecimal.valueOf(90));
            final UUID adminUuid = userAdmin.getUuid();
            final UUID userToEditUuid = userRegular.getUuid();
            uuidSettings(adminUuid, userToEditUuid);
            when(userRepository.existsByLoginAndLoginNot(newUserData.getLogin(), userRegular.getLogin())).thenReturn(true);

            //when //then
            assertThatThrownBy(() -> userService.updateUser(userToEditUuid, newUserData, adminUuid))
                    .isInstanceOf(DataIntegrityViolationException.class)
                    .hasMessage(String.format("Login %s is already in use", newUserData.getLogin()));
        }

        @Test
        @DisplayName("Should not update when email already exists")
        void shouldNotUpdateWhenEmailAlreadyExists() {
            //given
            final UserForm newUserData = new UserForm("piter", "Piotr", "Frąckiewicz", UserRole.USER,
                    "piotrek123", "frackiewicz.piotr@wp.pl", BigDecimal.valueOf(90));
            final UUID adminUuid = userAdmin.getUuid();
            final UUID userToEditUuid = userRegular.getUuid();
            uuidSettings(adminUuid, userToEditUuid);
            when(userRepository.existsByEmailAndEmailNot(newUserData.getEmail(), userRegular.getEmail())).thenReturn(true);

            //when //then
            assertThatThrownBy(() -> userService.updateUser(userToEditUuid, newUserData, adminUuid))
                    .isInstanceOf(DataIntegrityViolationException.class)
                    .hasMessage(String.format("Email %s is already in use", newUserData.getEmail()));
        }

        private void uuidSettings(UUID adminUuid, UUID userToEditUuid) {
            when(userRepository.findUserByUuid(adminUuid)).thenReturn(Optional.ofNullable(userAdmin));
            when(userRepository.findUserByUuid(userToEditUuid)).thenReturn(Optional.ofNullable(userRegular));
        }
    }

    @Nested
    @DisplayName("FILTER")
    class filter {

        @Test
        @DisplayName("Should not filter users when given UUID does not belong to admin")
        void shouldNotFilterUsersWhenGivenUuidDoesNotBelongToAdmin() {
            //given
            final UserFilter userFilter = new UserFilter();
            final UUID regularUserUuid = userRegular.getUuid();
            when(userRepository.findUserByUuid(regularUserUuid)).thenReturn(Optional.ofNullable(userRegular));

            //when //then
            assertThatThrownBy(() -> userService.getFilteredUsers(userFilter, regularUserUuid))
                    .isInstanceOf(SecurityException.class)
                    .hasMessage(String.format("Given UUID %s does not belong to admin", regularUserUuid));
        }

        @Test
        @DisplayName("Should not filter users when given UUID does exist")
        void shouldNotFilterUsersWhenGivenUuidDoesNotExist() {
            //given
            final UserFilter userFilter = new UserFilter();
            final UUID nonExistingUuid = UUID.randomUUID();
            when(userRepository.findUserByUuid(nonExistingUuid)).thenReturn(Optional.empty());

            //when //then
            assertThatThrownBy(() -> userService.getFilteredUsers(userFilter, nonExistingUuid))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(String.format("User with UUID %s does not exist", nonExistingUuid));
        }
    }
    
}
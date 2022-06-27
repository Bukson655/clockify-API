package pl.sb.projekt.user.service;

import pl.sb.projekt.user.dto.UserDto;
import pl.sb.projekt.user.dto.UserForm;
import pl.sb.projekt.user.model.UserRole;
import pl.sb.projekt.user.search.UserFilter;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUserByUuid(final UUID uuid);

    void deleteUserByUuid(final UUID uuid, final UUID adminUuid);

    void saveUser(final UserForm userForm, final UUID adminUuid);

    UserForm updateUser(final UUID uuid, final UserForm userForm, final UUID adminUuid);

    List<UserDto> getFilteredUsers(UserFilter userFilter, UUID adminUuid);

    void verifyRoleByUuid(final UUID uuid, final UserRole userRole);

}
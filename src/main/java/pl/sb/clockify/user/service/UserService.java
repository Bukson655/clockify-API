package pl.sb.clockify.user.service;

import pl.sb.clockify.user.dto.UserDto;
import pl.sb.clockify.user.dto.UserForm;
import pl.sb.clockify.user.model.UserRole;
import pl.sb.clockify.user.search.UserFilter;

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
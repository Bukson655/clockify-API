package pl.sb.projekt.user.service;

import pl.sb.projekt.user.dto.UserDto;
import pl.sb.projekt.user.dto.UserForm;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUserByUuid(final UUID uuid);

    void deleteUserByUuid(final UUID uuid);

    void saveUser(final UserForm userForm);

    UserForm updateUser(final UUID uuid, final UserForm userForm);

}
package pl.sb.clockify.user.mapper;

import pl.sb.clockify.user.dto.UserDto;
import pl.sb.clockify.user.dto.UserForm;
import pl.sb.clockify.user.model.User;

public class UserMapper {

    public static UserForm convertToForm(final User user) {
        final UserForm userForm = new UserForm();
        userForm.setLogin(user.getLogin());
        userForm.setFirstName(user.getFirstName());
        userForm.setLastName(user.getLastName());
        userForm.setUserRole(user.getUserRole());
        userForm.setPassword(user.getPassword());
        userForm.setEmail(user.getEmail());
        userForm.setCostPerHour(user.getCostPerHour());
        return userForm;
    }

    public static UserDto convertToDto(final User user) {
        final UserDto userDto = new UserDto();
        userDto.setLogin(user.getLogin());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUserRole(user.getUserRole());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setCostPerHour(user.getCostPerHour());
        return userDto;
    }

    public static User convertFromForm(final UserForm userForm) {
        final User user = new User();
        user.setLogin(userForm.getLogin());
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setUserRole(userForm.getUserRole());
        user.setPassword(userForm.getPassword());
        user.setEmail(userForm.getEmail());
        user.setCostPerHour(userForm.getCostPerHour());
        return user;
    }

    public static User setUserFields(final UserForm userForm, final User entity) {
        entity.setLogin(userForm.getLogin());
        entity.setFirstName(userForm.getFirstName());
        entity.setLastName(userForm.getLastName());
        entity.setUserRole(userForm.getUserRole());
        entity.setPassword(userForm.getPassword());
        entity.setEmail(userForm.getEmail());
        entity.setCostPerHour(userForm.getCostPerHour());
        return entity;
    }

}
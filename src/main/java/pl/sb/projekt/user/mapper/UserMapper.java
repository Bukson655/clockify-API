package pl.sb.projekt.user.mapper;

import org.springframework.stereotype.Service;
import pl.sb.projekt.user.dto.UserDto;
import pl.sb.projekt.user.model.User;

@Service
public class UserMapper {

    public UserDto convertToDto(final User user) {
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

    public User convertFromDto(final UserDto userDto) {
        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUserRole(userDto.getUserRole());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setCostPerHour(userDto.getCostPerHour());
        return user;
    }

}
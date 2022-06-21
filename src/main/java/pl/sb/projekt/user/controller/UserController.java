package pl.sb.projekt.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.sb.projekt.ObjectAlreadyExistsException;
import pl.sb.projekt.user.dto.UserDto;
import pl.sb.projekt.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login/")
    public UserDto getUserWithLogin(@RequestParam(required = false) String login) {
        return userService.getUserByLogin(login);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("")
    public UserDto createUser(@RequestBody UserDto userDto) {
        if (!userService.findUserByLogin(userDto.getLogin()) &&
                !userService.findUserByEmail(userDto.getEmail())) {
            userService.saveUser(userDto);
            return userDto;
        } else {
            throw new ObjectAlreadyExistsException();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @PatchMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        userService.updateUser(id, userDto);
        return userDto;
    }

}
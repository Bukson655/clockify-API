package pl.sb.clockify.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.sb.clockify.user.dto.UserDto;
import pl.sb.clockify.user.dto.UserForm;
import pl.sb.clockify.user.search.UserFilter;
import pl.sb.clockify.user.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/filter")
    public List<UserDto> getFilteredUsers(@RequestBody @Valid final UserFilter userFilter,
                                          @RequestParam(name = "uuid") final UUID adminUuid) {
        return userService.getFilteredUsers(userFilter, adminUuid);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserForm createUser(@RequestBody @Valid final UserForm userForm,
                               @RequestParam(name = "uuid") final UUID adminUuid) {
        userService.saveUser(userForm, adminUuid);
        return userForm;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{uuid}")
    public UserDto getUserByUuid(@PathVariable final UUID uuid) {
        return userService.getUserByUuid(uuid);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{uuid}")
    public UserForm updateUser(@PathVariable final UUID uuid,
                               @RequestBody @Valid final UserForm userForm,
                               @RequestParam(name = "uuid") final UUID adminUuid) {
        return userService.updateUser(uuid, userForm, adminUuid);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    public void deleteUserById(@PathVariable final UUID uuid,
                               @RequestParam(name = "uuid") UUID adminUuid) {
        userService.deleteUserByUuid(uuid, adminUuid);
    }

}
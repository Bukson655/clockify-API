package pl.sb.projekt.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sb.projekt.ObjectNotFoundException;
import pl.sb.projekt.user.dto.UserDto;
import pl.sb.projekt.user.mapper.UserMapper;
import pl.sb.projekt.user.model.User;
import pl.sb.projekt.user.model.UserRole;
import pl.sb.projekt.user.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAllUsers() {
        final List<User> allUsers = userRepository.findAll();
        return allUsers.stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(final Long id) {
        final User user = userRepository
                .findById(id)
                .orElseThrow(ObjectNotFoundException::new);
        return userMapper.convertToDto(user);
    }

    @Transactional
    public void deleteUserById(final Long id) {
        final User user = userRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
        userRepository.delete(user);
    }

    public UserDto saveUser(final UserDto userDto) {
        final User user = userMapper.convertFromDto(userDto);
        userRepository.save(user);
        return userDto;
    }

    public boolean findUserByLogin(final String login) {
        return userRepository.isLoginAlreadyRegistered(login) != 0;
    }

    public boolean findUserByEmail(final String email) {
        return userRepository.isEmailAlreadyRegistered(email) != 0;
    }

    @Transactional
    public UserDto updateUser(final Long id, final UserDto userDto) {
        // dlaczego zwraca nulle - w db ok
        return userRepository.findById(id)
                .map(user -> setUserFields(userDto, user))
                .map(userMapper::convertToDto)
                .orElseThrow(ObjectNotFoundException::new);
    }

    private User setUserFields(final UserDto source, final User target) {

        target.setFirstName(Objects.nonNull(source.getFirstName()) ?
                source.getFirstName() : target.getFirstName());

        target.setLastName(Objects.nonNull(source.getLastName()) ?
                source.getLastName() : target.getLastName());

        target.setUserRole(Objects.nonNull(source.getUserRole()) ?
                source.getUserRole() : target.getUserRole());

        target.setUserRole(Objects.nonNull(source.getUserRole()) ?
                source.getUserRole() : target.getUserRole());

        target.setPassword(Objects.nonNull(source.getPassword()) ?
                source.getPassword() : target.getPassword());

        if (source.getLogin() != null) {
            setNewLogin(source, target);
        }

        if (source.getEmail() != null) {
            setNewEmail(source, target);
        }
        if (source.getCostPerHour() != null) {
            target.setCostPerHour(source.getCostPerHour());
        }
        return target;
    }

    private void setNewLogin(final UserDto source, final User target) {
        if (!findUserByLogin(source.getLogin())) {
            target.setLogin(source.getLogin());
        } else {
            throw new IllegalArgumentException("Login already exists");
        }
    }

    private void setNewEmail(final UserDto source, final User target) {
        if (!findUserByEmail(source.getEmail())) {
            target.setEmail(source.getEmail());
        } else {
            throw new IllegalArgumentException("Email already exists");
        }
    }

    public UserDto getUserByLogin(final String login) {
        final User user = userRepository
                .findByLogin(login)
                .orElseThrow(ObjectNotFoundException::new);
        return userMapper.convertToDto(user);

    }

    public List<UserDto> getUsersWithFirstName(final String firstName) {
        return userRepository.findAllUsersWithFirstName(firstName)
                .stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getUserWithLastName(final String lastName) {
        return userRepository.findAllUsersWithLastName(lastName)
                .stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsersWithCostBetween(BigDecimal costFrom, BigDecimal costTo) {
        return userRepository.findAllUsersWithCostBetween(costFrom, costTo)
                .stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsersWithRole(UserRole userRole) {
        return userRepository.findAllUsersWithRole(userRole)
                .stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }
}
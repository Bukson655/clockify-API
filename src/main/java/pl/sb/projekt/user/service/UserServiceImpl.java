package pl.sb.projekt.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sb.projekt.exception.NotFoundException;
import pl.sb.projekt.user.dto.UserDto;
import pl.sb.projekt.user.dto.UserForm;
import pl.sb.projekt.user.mapper.UserMapper;
import pl.sb.projekt.user.model.User;
import pl.sb.projekt.user.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserByUuid(final UUID uuid) {
        return userRepository.findUserByUuid(uuid)
                .map(userMapper::convertToDto)
                .orElseThrow(() -> new NotFoundException(String.format("User with UUID %s does not exist", uuid)));
    }

    @Transactional
    public void deleteUserByUuid(final UUID uuid) {
        userRepository.deleteByUuid(uuid);
    }

    @Transactional
    public void saveUser(final UserForm userForm) {
        verifyLoginAndEmail(userForm);
        final User user = userMapper.convertFromForm(userForm);
        userRepository.save(user);
    }

    @Transactional
    public UserForm updateUser(final UUID uuid, final UserForm userForm) {
        User user = verifyLoginAndEmailFromExistingUser(uuid, userForm);
        return userMapper.convertToForm(userMapper.setUserFields(userForm, user));

    }

    private User verifyLoginAndEmailFromExistingUser(UUID uuid, UserForm userForm) {
        User entity = userRepository.findUserByUuid(uuid)
                .orElseThrow(() -> new NotFoundException(String.format("User with UUID %s does not exist", uuid)));
        if (userRepository.existsByLoginAndLoginNot(userForm.getLogin(), entity.getLogin())) {
            throw new DataIntegrityViolationException(
                    String.format("Login %s is already in use", userForm.getLogin()));
        }
        if (userRepository.existsByEmailAndEmailNot(userForm.getEmail(), entity.getEmail())) {
            throw new DataIntegrityViolationException(
                    String.format("Email %s is already in use", userForm.getEmail()));
        }
        return entity;
    }

    private void verifyLoginAndEmail(final UserForm userForm) {
        if (userRepository.existsByLogin(userForm.getLogin())) {
            throw new DataIntegrityViolationException(
                    String.format("Login %s is already in use", userForm.getLogin()));
        }
        if (userRepository.existsByEmail(userForm.getEmail())) {
            throw new DataIntegrityViolationException(
                    String.format("Email %s is already in use", userForm.getEmail()));
        }
    }

}
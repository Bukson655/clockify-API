package pl.sb.projekt.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sb.projekt.common.exception.NotFoundException;
import pl.sb.projekt.user.dto.UserDto;
import pl.sb.projekt.user.dto.UserForm;
import pl.sb.projekt.user.mapper.UserMapper;
import pl.sb.projekt.user.model.User;
import pl.sb.projekt.user.model.UserRole;
import pl.sb.projekt.user.repository.UserRepository;
import pl.sb.projekt.user.search.UserFilter;
import pl.sb.projekt.user.search.UserSpecification;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserByUuid(final UUID uuid) {
        return userRepository.findByUuid(uuid)
                .map(UserMapper::convertToDto)
                .orElseThrow(() -> new NotFoundException(String.format("User with UUID %s does not exist", uuid)));
    }

    @Transactional
    public void deleteUserByUuid(final UUID uuid, final UUID adminUuid) {
        verifyRoleByUuid(adminUuid, UserRole.ADMIN);
        userRepository.deleteByUuid(uuid);
    }

    @Transactional
    public void saveUser(final UserForm userForm, final UUID adminUuid) {
        verifyRoleByUuid(adminUuid, UserRole.ADMIN);
        verifyLoginAndEmail(userForm);
        final User user = UserMapper.convertFromForm(userForm);
        userRepository.save(user);
    }

    @Transactional
    public UserForm updateUser(final UUID uuid, final UserForm userForm, final UUID adminUuid) {
        verifyRoleByUuid(adminUuid, UserRole.ADMIN);
        User user = verifyLoginAndEmailFromExistingUser(uuid, userForm);
        return UserMapper.convertToForm(UserMapper.setUserFields(userForm, user));

    }

    public void verifyRoleByUuid(final UUID uuid, final UserRole userRole) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException(String.format("User with UUID %s does not exist", uuid)));
        if (!user.getUserRole().equals(userRole)) {
            throw new SecurityException(String.format("Given UUID %s does not belong to admin", uuid));
        }
    }

    public List<UserDto> getFilteredUsers(final UserFilter userFilter, final UUID adminUuid) {
        verifyRoleByUuid(adminUuid, UserRole.ADMIN);
        final UserSpecification userSpecification = new UserSpecification(userFilter);
        return userRepository.findAll(userSpecification)
                .stream()
                .map(UserMapper::convertToDto)
                .collect(Collectors.toList());
    }

    private User verifyLoginAndEmailFromExistingUser(final UUID uuid, final UserForm userForm) {
        User entity = userRepository.findByUuid(uuid)
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
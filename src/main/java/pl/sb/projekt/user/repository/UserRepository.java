package pl.sb.projekt.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sb.projekt.user.model.User;
import pl.sb.projekt.user.model.UserRole;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query ("SELECT COUNT(u) " +
            "FROM User u " +
            "WHERE u.login = ?1")
    int isLoginAlreadyRegistered(String login);

    @Query ("SELECT COUNT(u) " +
            "FROM User u " +
            "WHERE u.email = ?1")
    int isEmailAlreadyRegistered(String email);

    @Query ("SELECT u " +
            "FROM User u " +
            "WHERE u.login = ?1")
    Optional<User> findByLogin(String login);

    @Query ("SELECT u " +
            "FROM User u " +
            "WHERE u.firstName = ?1")
    List<User> findAllUsersWithFirstName(String firstName);

    @Query ("SELECT u " +
            "FROM User u " +
            "WHERE u.lastName = ?1")
    List<User> findAllUsersWithLastName(String lastName);

    @Query("SELECT u " +
            "FROM User u " +
            "WHERE u.costPerHour " +
            "BETWEEN ?1 AND ?2")
    List<User> findAllUsersWithCostBetween(BigDecimal costFrom, BigDecimal costTo);

    @Query("SELECT u " +
            "FROM User u " +
            "WHERE u.userRole = ?1")
    List<User> findAllUsersWithRole(UserRole userRole);
}
package pl.sb.projekt.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sb.projekt.user.model.User;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("SELECT u " +
            "FROM User u " +
            "LEFT JOIN FETCH u.projects p " +
            "LEFT JOIN FETCH u.records r " +
            "WHERE u.uuid = :uuid")
    Optional<User> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

    boolean existsByLoginAndLoginNot(String formLogin, String userLogin);

    boolean existsByEmailAndEmailNot(String formEmail, String userEmail);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);

    @Query("SELECT u " +
            "FROM User u " +
            "LEFT JOIN FETCH u.projects p " +
            "LEFT JOIN FETCH u.records r " +
            "WHERE u.uuid = :uuid " +
            "AND r.startDateTime > :lowerDateRange " +
            "AND r. endDateTime < :actualTime")
    Optional<User> findByUuidWithDateRange(UUID uuid, LocalDateTime lowerDateRange, LocalDateTime actualTime);

}
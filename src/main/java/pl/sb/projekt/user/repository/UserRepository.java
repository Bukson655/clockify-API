package pl.sb.projekt.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.sb.projekt.user.model.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

    boolean existsByLoginAndLoginNot(String formLogin, String userLogin);

    boolean existsByEmailAndEmailNot(String formEmail, String userEmail);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);

}
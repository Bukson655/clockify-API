package pl.sb.projekt.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sb.projekt.project.model.Project;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    @Query("SELECT p " +
            "FROM Project p " +
            "LEFT JOIN FETCH p.users u " +
            "LEFT JOIN FETCH p.records r " +
            "WHERE p.uuid = :uuid")
    Optional<Project> findByUuid(UUID uuid);

    boolean existsByTitle(String title);

    void deleteByUuid(UUID uuid);

    boolean existsByTitleAndTitleNot(String formTitle, String projectTitle);

    @Query("SELECT DISTINCT p " +
            "FROM Project p " +
            "LEFT JOIN FETCH p.users u " +
            "LEFT JOIN FETCH p.records r")
    List<Project> findAllProjectsWithUsers();

    @Query("SELECT DISTINCT p " +
            "FROM Project p " +
            "LEFT JOIN FETCH p.records r")
    List<Project> findAll();

    @Query("SELECT p " +
            "FROM Project p " +
            "LEFT JOIN FETCH p.users u " +
            "LEFT JOIN FETCH p.records r " +
            "WHERE p.uuid = :projectUuid " +
            "AND r.startDateTime > :lowerDateRange " +
            "AND r.endDateTime < :actualTime")
    Optional<Project> findByUuidWithDateRange(UUID projectUuid, LocalDateTime lowerDateRange, LocalDateTime actualTime);
}
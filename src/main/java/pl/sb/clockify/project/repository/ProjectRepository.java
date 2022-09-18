package pl.sb.clockify.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sb.clockify.project.model.Project;

import java.math.BigDecimal;
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

    @Query("SELECT p.budgetUse " +
            "FROM Project p " +
            "WHERE p.uuid = :uuid")
    Optional<BigDecimal> findBudgetUseByUuid(UUID uuid);

}
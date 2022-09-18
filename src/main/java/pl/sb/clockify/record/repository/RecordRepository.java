package pl.sb.clockify.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sb.clockify.record.model.Record;
import pl.sb.clockify.report.dto.ReportProjectProjection;
import pl.sb.clockify.report.dto.ReportUserProjection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query("SELECT DISTINCT r " +
            "FROM Record r " +
            "LEFT JOIN FETCH r.user u " +
            "LEFT JOIN FETCH r.project p " +
            "WHERE u.uuid = :userUuid")
    List<Record> findAllByUserUuid(UUID userUuid);

    void deleteByUuidAndUserUuid(UUID recordUuid, UUID userUuid);

    Optional<Record> findRecordByUuidAndUserUuid(UUID recordUuid, UUID userUuid);

    @Query(nativeQuery = true, value = "SELECT p.title AS title, u.cost_per_hour AS costPerHour, " +
            "sum(EXTRACT (HOUR FROM (r.end_date_time - r.start_date_time))) AS sumHours, " +
            "sum(r.cost_of_work) AS sumCost " +
            "FROM public.record r " +
            "LEFT JOIN public.user u on r.user_id = u.id " +
            "LEFT JOIN public.project p on r.project_id = p.id " +
            "WHERE u.uuid = ?1 " +
            "AND r.start_date_time > ?2 " +
            "AND r.end_date_time < ?3 " +
            "GROUP BY p.title, u.cost_per_hour")
    List<ReportUserProjection> findUserSummaryByUuidAndDateRange(UUID userUuid, LocalDateTime lowerDateRange, LocalDateTime actualTime);

    @Query(nativeQuery = true, value = "SELECT u.login AS title, " +
            "sum(EXTRACT (HOUR FROM (r.end_date_time - r.start_date_time))) AS sumHours, " +
            "sum(r.cost_of_work) AS sumCost " +
            "FROM public.record r " +
            "LEFT JOIN public.user u on r.user_id = u.id " +
            "LEFT JOIN public.project p on r.project_id = p.id " +
            "WHERE p.uuid = ?1 " +
            "AND r.start_date_time > ?2 " +
            "AND r.end_date_time < ?3 " +
            "GROUP BY u.login")
    List<ReportProjectProjection> findProjectSummaryByUuidAndDateRange(UUID projectUuid, LocalDateTime lowerDateRange, LocalDateTime actualTime);

}
package pl.sb.projekt.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.sb.projekt.record.model.Record;

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

}
package pl.sb.projekt.record.service;

import pl.sb.projekt.record.dto.RecordDto;
import pl.sb.projekt.record.dto.RecordForm;

import java.util.List;
import java.util.UUID;

public interface RecordService {

    List<RecordDto> getAllRecordsByUserUuid(final UUID userUuid);

    void saveRecord(final RecordForm recordFormCreate);

    void deleteRecord(final UUID recordUuid, final UUID userUuid);

    RecordDto updateRecord(final UUID recordUuid, final UUID userUuid, final RecordForm recordFormUpdate);
}

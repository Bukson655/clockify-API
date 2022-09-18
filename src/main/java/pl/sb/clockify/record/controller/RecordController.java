package pl.sb.clockify.record.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.sb.clockify.record.dto.RecordDto;
import pl.sb.clockify.record.dto.RecordForm;
import pl.sb.clockify.record.service.RecordService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<RecordDto> getAllRecords(@RequestParam(name = "uuid") final UUID userUuid) {
        return recordService.getAllRecordsByUserUuid(userUuid);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createRecord(@Valid @RequestBody final RecordForm recordFormCreate) {
        recordService.saveRecord(recordFormCreate);
    }

    @DeleteMapping("/{recordUuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecord(@PathVariable final UUID recordUuid, final @RequestParam(name = "uuid") UUID userUuid) {
        recordService.deleteRecord(recordUuid, userUuid);
    }

    @PutMapping("/{recordUuid}")
    @ResponseStatus(HttpStatus.OK)
    public RecordDto updateRecord(@PathVariable final UUID recordUuid, @RequestParam(name = "uuid") final UUID userUuid,
                                  @Valid @RequestBody final RecordForm recordFormUpdate) {
        return recordService.updateRecord(recordUuid, userUuid, recordFormUpdate);
    }

}
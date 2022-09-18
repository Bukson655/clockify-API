package pl.sb.clockify.record.mapper;

import pl.sb.clockify.project.model.Project;
import pl.sb.clockify.record.dto.RecordDto;
import pl.sb.clockify.record.dto.RecordForm;
import pl.sb.clockify.record.model.Record;
import pl.sb.clockify.user.model.User;

import java.math.BigDecimal;

public class RecordMapper {

    public static RecordDto convertToDto(final Record record) {
        final RecordDto recordDto = new RecordDto();
        recordDto.setStartDateTime(record.getStartDateTime());
        recordDto.setEndDateTime(record.getEndDateTime());
        recordDto.setProjectName(record.getProject().getTitle());
        recordDto.setDescription(record.getDescription());
        return recordDto;
    }

    public static Record convertFromForm(final RecordForm recordForm, final User user,
                                         final Project project, final BigDecimal cost) {
        final Record record = new Record();
        record.setStartDateTime(recordForm.getStartDateTime());
        record.setEndDateTime(recordForm.getEndDateTime());
        record.setDescription(recordForm.getDescription());
        record.setCostOfWork(cost);
        record.setUser(user);
        record.setProject(project);
        return record;
    }

    public static Record setRecordFields(final RecordForm recordForm, final Record entity,
                                         final Project project, final BigDecimal cost) {
        entity.setStartDateTime(recordForm.getStartDateTime());
        entity.setEndDateTime(recordForm.getEndDateTime());
        entity.setDescription(recordForm.getDescription());
        entity.setCostOfWork(cost);
        entity.setProject(project);
        entity.setUser(entity.getUser());
        return entity;
    }

}
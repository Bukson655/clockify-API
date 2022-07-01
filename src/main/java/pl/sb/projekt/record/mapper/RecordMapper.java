package pl.sb.projekt.record.mapper;

import pl.sb.projekt.project.model.Project;
import pl.sb.projekt.record.dto.RecordDto;
import pl.sb.projekt.record.dto.RecordForm;
import pl.sb.projekt.record.model.Record;
import pl.sb.projekt.user.model.User;

public class RecordMapper {

    public static RecordDto convertToDto(final Record record) {
        final RecordDto recordDto = new RecordDto();
        recordDto.setStartDateTime(record.getStartDateTime());
        recordDto.setEndDateTime(record.getEndDateTime());
        recordDto.setProjectName(record.getProject().getTitle());
        recordDto.setDescription(record.getDescription());
        return recordDto;
    }

    public static Record convertFromForm(final RecordForm recordForm, User user, Project project) {
        final Record record = new Record();
        record.setStartDateTime(recordForm.getStartDateTime());
        record.setEndDateTime(recordForm.getEndDateTime());
        record.setDescription(recordForm.getDescription());
        record.setUser(user);
        record.setProject(project);
        return record;
    }

    public static Record setRecordFields(final RecordForm recordForm, final Record entity, final Project project) {
        entity.setStartDateTime(recordForm.getStartDateTime());
        entity.setEndDateTime(recordForm.getEndDateTime());
        entity.setDescription(recordForm.getDescription());
        entity.setProject(project);
        entity.setUser(entity.getUser());
        return entity;
    }

}

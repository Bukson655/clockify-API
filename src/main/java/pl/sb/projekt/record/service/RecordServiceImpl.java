package pl.sb.projekt.record.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sb.projekt.common.exception.NotFoundException;
import pl.sb.projekt.project.model.Project;
import pl.sb.projekt.project.repository.ProjectRepository;
import pl.sb.projekt.record.dto.RecordDto;
import pl.sb.projekt.record.dto.RecordForm;
import pl.sb.projekt.record.mapper.RecordMapper;
import pl.sb.projekt.record.model.Record;
import pl.sb.projekt.record.repository.RecordRepository;
import pl.sb.projekt.user.model.User;
import pl.sb.projekt.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public List<RecordDto> getAllRecordsByUserUuid(final UUID userUuid) {
        return recordRepository.findAllByUserUuid(userUuid)
                .stream()
                .map(RecordMapper::convertToDto)
                .toList();
    }

    @Transactional
    public void saveRecord(final RecordForm recordForm) {
        verifyIfDateTimeIsCorrect(recordForm.getStartDateTime(), recordForm.getEndDateTime());
        User user = userRepository.findByUuid(recordForm.getUserUuid())
                .orElseThrow(() -> new NotFoundException(String.format("User with UUID %s does not exist", recordForm.getUserUuid())));
        Project project = projectRepository.findByUuid(recordForm.getProjectUuid())
                .orElseThrow(() -> new NotFoundException(String.format("Project with UUID %s does not exist", recordForm.getProjectUuid())));
        verifyIfUserBelongToProject(user, project);

        final Record record = recordRepository.save(RecordMapper.convertFromForm(recordForm, user, project));
        user.addRecord(record);
        project.addRecord(record);
    }

    @Transactional
    public void deleteRecord(final UUID recordUuid, final UUID userUuid) {
        final User user = userRepository.findByUuid(userUuid)
                .orElseThrow(() -> new NotFoundException(String.format("User with UUID %s does not exist", userUuid)));
        final Record record = recordRepository.findRecordByUuidAndUserUuid(recordUuid, userUuid)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Record with UUID %s and user with UUID %s does not match", recordUuid, userUuid)));
        final Project project = projectRepository.findByUuid(record.getProject().getUuid())
                .orElseThrow(() -> new NotFoundException(String.format("Project with UUID %s does not exist", record.getProject().getUuid())));

        recordRepository.deleteByUuidAndUserUuid(recordUuid, userUuid);
        user.removeRecord(record);
        project.removeRecord(record);
    }

    @Transactional
    public RecordDto updateRecord(final UUID recordUuid, final UUID userUuid, final RecordForm recordForm) {
        verifyIfDateTimeIsCorrect(recordForm.getStartDateTime(), recordForm.getEndDateTime());
        Project project = projectRepository.findByUuid(recordForm.getProjectUuid())
                .orElseThrow(() -> new NotFoundException(String.format("Project with UUID %s does not exist", recordForm.getProjectUuid())));
        User user = userRepository.findByUuid(userUuid)
                .orElseThrow(() -> new NotFoundException(String.format("User with UUID %s does not exist", userUuid)));
        verifyIfUserBelongToProject(user, project);

        final Record record = recordRepository.findRecordByUuidAndUserUuid(recordUuid, userUuid)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Record with UUID %s and user with UUID %s does not match", recordUuid, userUuid)));
        return RecordMapper.convertToDto(RecordMapper.setRecordFields(recordForm, record, project));
    }

    private void verifyIfUserBelongToProject(User user, Project project) {
        if (!project.getUsers().contains(user)) {
            throw new NotFoundException(
                    String.format("User with UUID %s does not belong to project with UUID %s", user.getUuid(), project.getUuid()));
        }
    }

    public void verifyIfDateTimeIsCorrect(final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        if (startDateTime.isAfter(endDateTime)) {
            throw new DataIntegrityViolationException(
                    "startDateTime cannot be after endDateTime");
        }
    }

}
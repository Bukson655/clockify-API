package pl.sb.clockify.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import pl.sb.clockify.common.entity.EntityAbstract;
import pl.sb.clockify.project.model.Project;
import pl.sb.clockify.record.model.Record;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@Table(name = "user", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class User extends EntityAbstract {

    @Column(name = "login", unique = true, nullable = false, length = 50)
    private String login;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false, length = 7)
    private UserRole userRole;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "email", unique = true, nullable = false, length = 150)
    private String email;

    @Column(name = "cost_per_hour", nullable = false, precision = 10, scale = 2)
    private BigDecimal costPerHour;

    @ManyToMany(mappedBy = "users")
    @Builder.Default
    private Set<Project> projects = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private Set<Record> records = new HashSet<>();

    public void addProject(Project project) {
        projects.add(project);
    }

    public void removeProject(Project project) {
        projects.remove(project);
    }

    public void addRecord(Record record) {
        this.records.add(record);
        record.setUser(this);
    }

    public void removeRecord(Record record) {
        this.records.remove(record);
        record.setUser(null);
    }

}
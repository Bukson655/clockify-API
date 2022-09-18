package pl.sb.clockify.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import pl.sb.clockify.common.entity.EntityAbstract;
import pl.sb.clockify.record.model.Record;
import pl.sb.clockify.user.model.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@Table(name = "project", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class Project extends EntityAbstract {

    @Column(name = "title", unique = true, nullable = false, length = 50)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "budget", nullable = false, precision = 10, scale = 2)
    private BigDecimal budget;

    @Column(name = "budget_use", nullable = false)
    private BigDecimal budgetUse = BigDecimal.ZERO;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "project_user",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Builder.Default
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @Builder.Default
    private Set<Record> records = new HashSet<>();

    public void addUser(User user) {
        this.users.add(user);
        user.addProject(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.removeProject(this);
    }

    public void addRecord(Record record) {
        this.records.add(record);
        record.setProject(this);
    }

    public void removeRecord(Record record) {
        this.records.remove(record);
        record.setProject(null);
    }

}
package pl.sb.projekt.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import pl.sb.projekt.common.entity.EntityAbstract;
import pl.sb.projekt.project.model.Project;

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

}
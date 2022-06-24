package pl.sb.projekt.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "user", schema = "public")
@NoArgsConstructor
@FieldNameConstants
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false)
    private final UUID uuid = UUID.randomUUID();

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

}
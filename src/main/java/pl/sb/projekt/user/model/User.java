package pl.sb.projekt.user.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "user", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid = UUID.randomUUID();
    @EqualsAndHashCode.Include
    private String login;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.USER;
    private String password;
    @EqualsAndHashCode.Include
    private String email;
    private BigDecimal costPerHour;

}
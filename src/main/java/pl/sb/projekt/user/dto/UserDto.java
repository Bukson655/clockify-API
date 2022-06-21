package pl.sb.projekt.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.sb.projekt.user.model.UserRole;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private String login;
    private String firstName;
    private String lastName;
    private UserRole userRole = UserRole.USER;
    private String password;
    private String email;
    private BigDecimal costPerHour;

}
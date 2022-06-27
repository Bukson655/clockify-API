package pl.sb.projekt.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.sb.projekt.user.model.UserRole;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {

    @NotBlank(message = "login cannot be blank")
    @Size(max = 50)
    private String login;

    @NotBlank(message = "firstName cannot be blank")
    @Size(max = 50)
    private String firstName;

    @NotBlank(message = "lastName cannot be blank")
    @Size(max = 50)
    private String lastName;

    @NotNull(message = "userRole cannot be null")
    private UserRole userRole = UserRole.USER;

    @NotBlank(message = "password cannot be blank")
    @Size(max = 100)
    private String password;

    @NotNull
    @Email(message = "wrong email format")
    @Size(max = 150)
    private String email;

    @NotNull
    @Positive(message = "costPerHour has to be greater than 0")
    private BigDecimal costPerHour;

}
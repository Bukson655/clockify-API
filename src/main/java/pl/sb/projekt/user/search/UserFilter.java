package pl.sb.projekt.user.search;

import lombok.Getter;
import lombok.Setter;
import pl.sb.projekt.user.model.UserRole;
import java.math.BigDecimal;

@Getter
@Setter
public class UserFilter {

    private String login;
    private String firstName;
    private String lastName;
    private UserRole userRole;
    private BigDecimal costFrom;
    private BigDecimal costTo;

}
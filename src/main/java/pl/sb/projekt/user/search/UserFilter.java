package pl.sb.projekt.user.search;

import lombok.Getter;
import pl.sb.projekt.user.model.UserRole;
import java.math.BigDecimal;

@Getter
public class UserFilter {

    private String login;
    private String firstName;
    private String lastName;
    private UserRole userRole;
    private BigDecimal costFrom;
    private BigDecimal costTo;

}
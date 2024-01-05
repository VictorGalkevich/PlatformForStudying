package by.itstep.application.registration;

import by.itstep.application.entity.type.Role;
import by.itstep.application.entity.type.UserType;
import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class RegistrationRequest {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
    private UserType type;
}

package by.itstep.application.dto;

import lombok.Value;

@Value
public class UserReadDto {
    Long id;
    String username;
    String firstname;
    String lastname;
    String email;
}

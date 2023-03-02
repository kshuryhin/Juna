package ua.pp.juna.authenticationservice.controller.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.pp.juna.authenticationservice.model.Role;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsResponse implements Serializable {
    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private Role role;
}

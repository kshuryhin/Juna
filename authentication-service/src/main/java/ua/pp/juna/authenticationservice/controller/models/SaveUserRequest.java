package ua.pp.juna.authenticationservice.controller.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.pp.juna.authenticationservice.model.User;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveUserRequest implements Serializable {
    private User userDetails;
}

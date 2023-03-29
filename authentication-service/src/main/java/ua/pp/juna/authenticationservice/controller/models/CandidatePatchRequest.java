package ua.pp.juna.authenticationservice.controller.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CandidatePatchRequest {
    private String email;
    private String newPassword;
}

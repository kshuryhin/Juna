package ua.pp.juna.vacanciesservice.controller.candidates.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChangePasswordRequest {
    private String email;
    private String newPassword;
}

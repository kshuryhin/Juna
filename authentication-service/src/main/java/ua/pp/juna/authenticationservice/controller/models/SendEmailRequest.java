package ua.pp.juna.authenticationservice.controller.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SendEmailRequest {
    private List<String> recipients;
    private String msgBody;
    private String subject;
}

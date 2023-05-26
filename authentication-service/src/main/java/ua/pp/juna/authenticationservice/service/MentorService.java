package ua.pp.juna.authenticationservice.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ua.pp.juna.authenticationservice.model.User;

public interface MentorService extends UserDetailsService {
    User updateMentor(final User user, String token);
}

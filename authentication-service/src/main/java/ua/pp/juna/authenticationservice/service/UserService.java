package ua.pp.juna.authenticationservice.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ua.pp.juna.authenticationservice.model.Role;
import ua.pp.juna.authenticationservice.model.User;

public interface UserService extends UserDetailsService {
    User save(User user);
}

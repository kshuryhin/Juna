package ua.pp.juna.authenticationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.pp.juna.authenticationservice.controller.models.SaveUserRequest;
import ua.pp.juna.authenticationservice.controller.models.UserDetailsResponse;
import ua.pp.juna.authenticationservice.model.Role;
import ua.pp.juna.authenticationservice.model.User;

@Service
public class UserServiceImpl implements UserService {
    private final RestTemplate restTemplate = new RestTemplate();

    private final String HOST = "http://localhost:8085";
    private final String PATH = "/userDetails/{email}";

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserDetailsResponse userDetails = restTemplate.getForObject(HOST+PATH, UserDetailsResponse.class, username);
        if (userDetails == null) throw new UsernameNotFoundException("Invalid credentials!");

        return User.builder()
                .id(userDetails.getId())
                .email(userDetails.getEmail())
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .password(userDetails.getPassword())
                .role(userDetails.getRole())
                .build();
    }

    public User save(User user) {
        restTemplate.postForObject(HOST + "/" + user.getRole().name().toLowerCase(), new SaveUserRequest(user), String.class);
        return user;
    }
}

package ua.pp.juna.authenticationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.pp.juna.authenticationservice.controller.models.SaveUserRequest;
import ua.pp.juna.authenticationservice.model.User;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RestTemplate restTemplate;

    private final String HOST = "http://localhost:8085";
    private final String PATH = "/userDetails/{email}";

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        final User userDetails = restTemplate.getForObject(HOST+PATH, User.class, username);
        if (userDetails == null) throw new UsernameNotFoundException("Invalid credentials!");

        return userDetails;
    }

    public User save(User user) {
        restTemplate.postForObject(HOST + "/" + user.getRole().name().toLowerCase(), new SaveUserRequest(user), String.class);
        return user;
    }

    public User updateUser(User user, Long id, String token) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        final HttpEntity<User> userHttpEntity = new HttpEntity<>(user, headers);
        return restTemplate.exchange(HOST+PATH, HttpMethod.PUT, userHttpEntity, User.class, id).getBody();
    }
}

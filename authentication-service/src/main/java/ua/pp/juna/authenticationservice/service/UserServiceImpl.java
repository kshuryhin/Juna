package ua.pp.juna.authenticationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.pp.juna.authenticationservice.controller.models.CandidatePatchRequest;
import ua.pp.juna.authenticationservice.controller.models.SaveUserRequest;
import ua.pp.juna.authenticationservice.model.User;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RestTemplate restTemplate;

    private final String HOST = "http://gateway-service";
    private final String PATH = "/userDetails/{email}";

    @Override
    public User loadUserByUsername(final String username) throws UsernameNotFoundException {
        final var userDetails = restTemplate.getForObject(HOST+PATH, User.class, username);
        if (userDetails == null) throw new UsernameNotFoundException("Invalid credentials!");

        return userDetails;
    }

    public User save(final User user) {
        restTemplate.postForObject(HOST + "/" + user.getRole().name().toLowerCase(), new SaveUserRequest(user), String.class);
        return user;
    }

    public User updateUser(final User user, final String token) {
        final var headers = new HttpHeaders();
        headers.add("Authorization", token);
        final var userHttpEntity = new HttpEntity<>(user, headers);
        return restTemplate.exchange(HOST+PATH, HttpMethod.PUT, userHttpEntity, User.class, user.getId()).getBody();
    }

    @Override
    public void patchUser(String email, String newPassword, String token) {
        final var headers = new HttpHeaders();
        final var request = CandidatePatchRequest.builder().newPassword(newPassword).email(email).build();
        final var httpEntity = new HttpEntity<>(request, headers);
        headers.add("Authorization", token);
        restTemplate.exchange(HOST+"/userDetails", HttpMethod.PATCH, httpEntity, String.class);
    }
}

package ua.pp.juna.authenticationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.pp.juna.authenticationservice.model.User;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService{
    private final RestTemplate restTemplate;
    private final String HOST = "http://gateway-service";
    private final String PATH = "/mentors/email/{email}";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userDetails = restTemplate.getForObject(HOST+PATH, User.class, email);
        return userDetails;
    }

    public User updateMentor(final User user, final String token) {
        final var headers = new HttpHeaders();
        headers.add("Authorization", token);
        final var userHttpEntity = new HttpEntity<>(user, headers);
        return restTemplate.exchange(HOST+PATH, HttpMethod.PUT, userHttpEntity, User.class, user.getId()).getBody();
    }
}

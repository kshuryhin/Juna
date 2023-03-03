package ua.pp.juna.authenticationservice.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;
import ua.pp.juna.authenticationservice.model.Role;
import ua.pp.juna.authenticationservice.model.User;

import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class UserServiceTest {
     private UserService  userService;
    private final static String EMAIL = "ksurygin5@gmail.com";
    private final static String PASSWORD = "1234";
    private final static String TOKEN = "generated_token";
    private final static String FIRST_NAME = "Kostia";
    private final static String LAST_NAME = "Shuryhin";


     @Mock
     private RestTemplate restTemplate;

     @BeforeEach
     void setUp() {
         MockitoAnnotations.initMocks(this);
         userService = new UserServiceImpl(restTemplate);
     }

    @Test
    void saveUser_happyPath() {
        //arrange
        final var expected = User.builder()
                .id(1L)
                .lastName(LAST_NAME)
                .firstName(FIRST_NAME)
                .role(Role.CANDIDATES)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
        when(restTemplate.postForObject(any(), any(), any())).thenReturn(expected);

        //act
        final User actual = userService.save(expected);

        //assert
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void loadByUserName_happyPath() {
         //arrange
        final var expected = User.builder()
                .id(1L)
                .lastName(LAST_NAME)
                .firstName(FIRST_NAME)
                .role(Role.CANDIDATES)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
        when(restTemplate.getForObject(any(), any(), any(String.class))).thenReturn(expected);

        //act
        final var actual = userService.loadUserByUsername(expected.getEmail());

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void loadByUserName_throw_userNameNotFoundException() {
         //arrange
        final var expected = new UsernameNotFoundException("Invalid credentials!");
        when(restTemplate.getForObject(any(), any(), eq(EMAIL))).thenReturn(null);

        //act, assert
        assertThatThrownBy(() -> userService.loadUserByUsername(EMAIL))
                .isInstanceOf(expected.getClass())
                .hasMessage(expected.getMessage());
    }

    @Test
    void updateUser_happyPath() {
         //arrange
        final var user = User.builder()
                .id(1L)
                .lastName(LAST_NAME)
                .firstName(FIRST_NAME)
                .role(Role.CANDIDATES)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
        final var expected = user.withLoggedIn(false);
        final var headers = new HttpHeaders();
        headers.add("Authorization", TOKEN);
        final var entity = new HttpEntity<>(user, headers);

        when(restTemplate.exchange(any(), eq(HttpMethod.PUT), eq(entity), eq(User.class), eq(user.getId())))
                .thenReturn(ResponseEntity.of(Optional.of(expected)));

        //act
        final var actual = userService.updateUser(user, TOKEN);

        //assert
        assertThat(actual).isEqualTo(expected);
    }
}

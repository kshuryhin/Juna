package ua.pp.juna.authenticationservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;
import ua.pp.juna.authenticationservice.config.JwtService;
import ua.pp.juna.authenticationservice.model.Role;
import ua.pp.juna.authenticationservice.model.User;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class JwtServiceTest {
    private JwtService jwtService;
    private final static String EMAIL = "ksurygin5@gmail.com";
    private final static String PASSWORD = "1234";
    private final static String TOKEN = "generated_token";
    private final static String FIRST_NAME = "Kostia";
    private final static String LAST_NAME = "Shuryhin";
    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    @Test
    void generateToken_happyPath() {
        //arrange
        final var user = User.builder()
                .id(1L)
                .lastName(LAST_NAME)
                .firstName(FIRST_NAME)
                .role(Role.CANDIDATES)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        //act
        final var actual = jwtService.generateToken(user);

        //assert
        assertThat(actual).isNotNull();
        assertThat(actual).isNotEmpty();
    }
}

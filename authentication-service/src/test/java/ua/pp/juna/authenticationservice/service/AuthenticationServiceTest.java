package ua.pp.juna.authenticationservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.pp.juna.authenticationservice.config.JwtService;
import ua.pp.juna.authenticationservice.controller.models.*;
import ua.pp.juna.authenticationservice.model.Role;
import ua.pp.juna.authenticationservice.model.User;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthenticationServiceTest {
    private AuthenticationService authenticationService;
    @Mock
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    private final static String EMAIL = "ksurygin5@gmail.com";
    private final static String PASSWORD = "1234";

    private final static String ENCODED_PASSWORD = "encoded_password";
    private final static String TOKEN = "generated_token";
    private final static String FIRST_NAME = "Kostia";
    private final static String LAST_NAME = "Shuryhin";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        authenticationService = new AuthenticationService(userService, passwordEncoder, jwtService, authenticationManager);
    }

    @Test
    void register_happyPath() {
        //arrange
        final var registerRequest = RegisterRequest.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .role(Role.CANDIDATES)
                .build();
        final var user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(ENCODED_PASSWORD)
                .role(registerRequest.getRole())
                .isLoggedIn(true)
                .build();
        final var expected = AuthenticationResponse.builder()
                        .token(TOKEN)
                        .role(user.getRole())
                        .build();

        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn(ENCODED_PASSWORD);
        when(userService.save(user)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn(TOKEN);

        //act
        final var actual = authenticationService.register(registerRequest);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void authenticate_happyPath() {
        //arrange
        final var authenticationRequest = AuthenticationRequest.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();
        final var user = User.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .password(ENCODED_PASSWORD)
                .role(Role.CANDIDATES)
                .isLoggedIn(true)
                .build();
        final var expected = AuthenticationResponse.builder()
                .token(TOKEN)
                .role(user.getRole())
                .build();

        when(userService.loadUserByUsername(authenticationRequest.getEmail())).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn(TOKEN);
        when(userService.updateUser(user, TOKEN)).thenReturn(user);
        when(authenticationManager.authenticate( any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(any(UsernamePasswordAuthenticationToken.class));

        //act
        final var actual = authenticationService.authenticate(authenticationRequest);

        //assert
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void authenticate_throw_userNameNotFoundException(){
        //arrange
        final var authenticationRequest = AuthenticationRequest.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();
        final var expected = new UsernameNotFoundException("Invalid credential!");

        when(authenticationManager.authenticate( any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(expected);

        //act, assert
        assertThatThrownBy(() -> authenticationService.authenticate(authenticationRequest))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage(expected.getMessage());
    }

    @Test
    void updateToken_happyPath() {
        //arrange
        final var exchangeRequest = ExchangeRequest.builder()
                .email(EMAIL)
                .build();
        final var user = User.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .password(ENCODED_PASSWORD)
                .role(Role.CANDIDATES)
                .isLoggedIn(true)
                .build();
        final var expected = AuthenticationResponse.builder()
                .token(TOKEN)
                .role(user.getRole())
                .build();

        when(userService.loadUserByUsername(exchangeRequest.getEmail())).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn(TOKEN);

        //act
        final var actual = authenticationService.updateToken(exchangeRequest);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void updateToken_throwsAuthorizationServiceException() {
        //arrange
        final var exchangeRequest = ExchangeRequest.builder()
                .email(EMAIL)
                .build();
        final var user = User.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .password(ENCODED_PASSWORD)
                .role(Role.CANDIDATES)
                .isLoggedIn(false)
                .build();
        final var expected =  new AuthorizationServiceException("User is logged out!");

        when(userService.loadUserByUsername(exchangeRequest.getEmail())).thenReturn(user);

        //act, assert
        assertThatThrownBy(() -> authenticationService.updateToken(exchangeRequest))
                .isInstanceOf(expected.getClass())
                .hasMessage(expected.getMessage());
    }

    @Test
    void logout_happyPath() {
        //arrange
        final var user = User.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .password(ENCODED_PASSWORD)
                .role(Role.CANDIDATES)
                .isLoggedIn(true)
                .build();

        when(userService.loadUserByUsername(EMAIL)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn(TOKEN);
        when(userService.updateUser(user.withLoggedIn(false), TOKEN)).thenReturn(user.withLoggedIn(false));

        //act
        authenticationService.logout(EMAIL);

        //assert
        verify(userService).updateUser(user.withLoggedIn(false), TOKEN);
    }

    @Test
    void changePassword_happyPath(){
        //arrange
        final var expected = ChangePasswordResponse.builder().success(true).build();
        final var newPassword = "12345";
        final var changePasswordRequest = ChangePasswordRequest.builder().oldPassword(PASSWORD).newPassword(newPassword).build();
        final var user = User.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .password(ENCODED_PASSWORD)
                .role(Role.CANDIDATES)
                .isLoggedIn(true)
                .build();
        when(userService.loadUserByUsername(EMAIL)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn(TOKEN);
        when(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).thenReturn(true);

        //act
        final var actual = authenticationService.changePassword(changePasswordRequest, EMAIL);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void changePassword_returnsSuccessFalse(){
        //arrange
        final var expected = ChangePasswordResponse.builder().success(false).build();
        final var newPassword = "12345";
        final var changePasswordRequest = ChangePasswordRequest.builder().oldPassword(PASSWORD).newPassword(newPassword).build();
        final var user = User.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .password(ENCODED_PASSWORD)
                .role(Role.CANDIDATES)
                .isLoggedIn(true)
                .build();
        when(userService.loadUserByUsername(EMAIL)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn(TOKEN);
        when(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).thenReturn(false);

        //act
        final var actual = authenticationService.changePassword(changePasswordRequest, EMAIL);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

}

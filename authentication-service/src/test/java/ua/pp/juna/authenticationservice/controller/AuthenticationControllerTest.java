package ua.pp.juna.authenticationservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import ua.pp.juna.authenticationservice.controller.models.*;
import ua.pp.juna.authenticationservice.model.Role;
import ua.pp.juna.authenticationservice.service.AuthenticationService;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AuthenticationControllerTest {
    private AuthenticationController authenticationController;

    private final static String EMAIL = "ksurygin5@gmail.com";
    private final static String PASSWORD = "1234";
    private final static String TOKEN = "generated_token";
    private final static String FIRST_NAME = "Kostia";
    private final static String LAST_NAME = "Shuryhin";

    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        authenticationController = new AuthenticationController(authenticationService);
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
        final var authenticationResponse = AuthenticationResponse.builder()
                .token(TOKEN)
                .role(Role.CANDIDATES)
                .build();
        final var expected = ResponseEntity.ok().body(authenticationResponse);

        when(authenticationService.register(registerRequest)).thenReturn(authenticationResponse);

        //act
        final var actual = authenticationController.register(registerRequest);

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
        final var authenticationResponse = AuthenticationResponse.builder()
                .token(TOKEN)
                .role(Role.CANDIDATES)
                .build();
        final var expected = ResponseEntity.ok().body(authenticationResponse);

        when(authenticationService.authenticate(authenticationRequest)).thenReturn(authenticationResponse);

        //act
        final var actual = authenticationController.authenticate(authenticationRequest);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void exchange_happyPath() {
        //arrange

        final var authenticationResponse = AuthenticationResponse.builder()
                .token(TOKEN)
                .role(Role.CANDIDATES)
                .build();
        final var expected = ResponseEntity.ok().body(authenticationResponse);

        when(authenticationService.updateToken(EMAIL)).thenReturn(authenticationResponse);

        //act
        final var actual = authenticationController.updateToken(EMAIL);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void logout_happyPath() {
        //arrange
        final var expected = ResponseEntity.ok().build();

        //act
        final var actual = authenticationController.logout(EMAIL);

        //assert
        verify(authenticationService).logout(EMAIL);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void changePassword_happyPath(){
        //arrange
        final var changePasswordResponse = ChangePasswordResponse.builder().success(true).build();
        final var expected = ResponseEntity.ok(changePasswordResponse);
        final var newPassword = "12345";
        final var changePasswordRequest = ChangePasswordRequest.builder().oldPassword(PASSWORD).newPassword(newPassword).build();
        when(authenticationService.changePassword(changePasswordRequest, EMAIL)).thenReturn(changePasswordResponse);

        //act
        final var actual = authenticationController.changePassword(EMAIL, changePasswordRequest);

        //assert
        assertThat(actual).isEqualTo(expected);
    }
}

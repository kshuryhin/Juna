package ua.pp.juna.vacanciesservice.controller.userDetails;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import ua.pp.juna.vacanciesservice.controller.UserDetailsController;
import ua.pp.juna.vacanciesservice.controller.candidates.models.ChangePasswordRequest;
import ua.pp.juna.vacanciesservice.domain.UserDetails;
import ua.pp.juna.vacanciesservice.service.UserDetailsService;

class UserDetailsControllerTest {
    @Mock
    private UserDetailsService userDetailsService;

    private UserDetailsController userDetailsController;

    private static final String EMAIL = "test@gmail.com";

    private static final Long ID = 1L;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        this.userDetailsController = new UserDetailsController(userDetailsService);
    }

    @Test
    void getUserDetailsByEmail_happyPath(){
        //arrange
        final var userDetails = UserDetails.builder()
                .id(ID)
                .email(EMAIL)
                .build();
        final var expected = ResponseEntity.ok(userDetails);

        when(userDetailsService.getUserDetailsByEmail(EMAIL)).thenReturn(userDetails);

        //act
        final var actual = userDetailsController.getUserDetailsByEmail(EMAIL);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void updateUserDetails_happyPath(){
        //arrange
        final var oldUserDetails = UserDetails.builder()
                .id(ID)
                .password("123")
                .build();
        final var updatedUserDetails = UserDetails.builder()
                .id(ID)
                .password("1234")
                .build();
        final var expected = ResponseEntity.ok(updatedUserDetails);

        when(userDetailsService.updateUserDetails(updatedUserDetails, ID)).thenReturn(updatedUserDetails);

        //act
        final var actual = userDetailsController.updateUserDetails(updatedUserDetails, ID);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void patchUserDetails_happyPath(){
        //arrange
        final var changePasswordRequest = ChangePasswordRequest.builder()
                .email(EMAIL)
                .newPassword("1234")
                .build();
        final var updatedUserDetails = UserDetails.builder()
                .email(EMAIL)
                .password("1234")
                .build();
        final var expected = ResponseEntity.ok(updatedUserDetails);

        when(userDetailsService.patchUserDetails(changePasswordRequest.getEmail(), changePasswordRequest.getNewPassword()))
                .thenReturn(updatedUserDetails);

        //act
        final var actual = userDetailsController.patchUserDetails(changePasswordRequest);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

}

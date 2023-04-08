package ua.pp.juna.vacanciesservice.services.userDetails;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.pp.juna.vacanciesservice.domain.UserDetails;
import ua.pp.juna.vacanciesservice.repo.UserDetailsRepository;
import ua.pp.juna.vacanciesservice.service.UserDetailsService;
import ua.pp.juna.vacanciesservice.service.UserDetailsServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class UserDetailsServiceTest {
    @Mock
    private UserDetailsRepository userDetailsRepository;

    private UserDetailsService userDetailsService;

    private static final Long ID = 1L;

    private static final String EMAIL = "test@gmail.com";

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        this.userDetailsService = new UserDetailsServiceImpl(userDetailsRepository);
    }

    @Test
    void saveUserDetails_happyPath(){
        //arrange
        final var userDetails = UserDetails.builder()
                .id(ID)
                .build();
        when(userDetailsRepository.save(userDetails)).thenReturn(userDetails);

        //act
        final var actual = userDetailsService.saveUserDetails(userDetails);

        //assert
        assertThat(actual).isEqualTo(userDetails);
    }

    @Test
    void getUserDetailsById_happyPath(){
        //arrange
        final var userDetails = UserDetails.builder()
                .id(ID)
                .build();
        when(userDetailsRepository.findById(ID)).thenReturn(Optional.of(userDetails));

        //act
        final var actual = userDetailsService.getUserDetailsById(ID);

        //assert
        assertThat(actual).isEqualTo(userDetails);
    }

    @Test
    void getAllUserDetails_happyPath(){
        //arrange
        final var userDetails1 = UserDetails.builder()
                .id(ID)
                .build();
        final var userDetails2 = UserDetails.builder()
                .id(2L)
                .build();
        final var expected = List.of(userDetails1, userDetails2);

        when(userDetailsRepository.findAll()).thenReturn(List.of(userDetails1, userDetails2));

        //act
        final var actual = userDetailsService.getAllUserDetails();

        //assert
        assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    void getUserDetailsByEmail_happyPath(){
        //arrange
        final var userDetails = UserDetails.builder()
                .id(ID)
                .email(EMAIL)
                .build();
        when(userDetailsRepository.findUserDetailsByEmail(EMAIL)).thenReturn(Optional.of(userDetails));

        //act
        final var actual = userDetailsService.getUserDetailsByEmail(EMAIL);

        //assert
        assertThat(actual).isEqualTo(userDetails);
    }

    @Test
    void updateUserDetails_happyPath(){
        //arrange
        final var updatedUserDetails = UserDetails.builder()
                .id(ID)
                .password("1234")
                .build();
        final var oldUserDetails = UserDetails.builder()
                .id(ID)
                .password("123")
                .build();

        when(userDetailsRepository.findById(ID)).thenReturn(Optional.of(oldUserDetails));
        when(userDetailsRepository.save(updatedUserDetails)).thenReturn(updatedUserDetails);

        //act
        final var actual = userDetailsService.updateUserDetails(updatedUserDetails, ID);

        //assert
        assertThat(actual).isEqualTo(updatedUserDetails);
    }

    @Test
    void patchUserDetails_happyPath(){
        //arrange
        final var oldUserDetails = UserDetails.builder()
                .id(ID)
                .email(EMAIL)
                .password("1234")
                .build();
        final var patchedUserDetails = UserDetails.builder()
                .id(ID)
                .email(EMAIL)
                .password("123")
                .build();
        when(userDetailsRepository.findUserDetailsByEmail(EMAIL)).thenReturn(Optional.of(oldUserDetails));
        when(userDetailsRepository.save(patchedUserDetails)).thenReturn(patchedUserDetails);

        //act
        final var actual = userDetailsService.patchUserDetails(EMAIL, "123");

        //assert
        assertThat(actual).isEqualTo(patchedUserDetails);
    }

    @Test
    void deleteUserDetails_happyPath(){
        //act
        userDetailsService.deleteUserDetails(ID);

        //assert
        verify(userDetailsRepository).deleteById(ID);
    }
}

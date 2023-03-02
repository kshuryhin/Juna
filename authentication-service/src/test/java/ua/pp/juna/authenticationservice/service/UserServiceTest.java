package ua.pp.juna.authenticationservice.service;


import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import ua.pp.juna.authenticationservice.model.Role;
import ua.pp.juna.authenticationservice.model.User;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {
     private UserService  userService;


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
        final User expected = User.builder()
                .id(1L)
                .lastName("Shuyrhin")
                .firstName("Kostiantyn")
                .role(Role.CANDIDATES)
                .email("ksurygin5@gmail.com")
                .password("1234")
                .build();
        when(restTemplate.postForObject(any(), any(), any())).thenReturn(expected);

        //act
        final User actual = userService.save(expected);

        //assert
        assertThat(actual).isEqualTo(expected);

    }
}

package ua.pp.juna.vacanciesservice.services.employers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.pp.juna.vacanciesservice.domain.UserDetails;
import ua.pp.juna.vacanciesservice.domain.employers.Employer;
import ua.pp.juna.vacanciesservice.repo.UserDetailsRepository;
import ua.pp.juna.vacanciesservice.repo.employers.EmployerRepository;
import ua.pp.juna.vacanciesservice.service.employers.EmployerService;
import ua.pp.juna.vacanciesservice.service.employers.EmployerServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmployerServiceTest {

    @Mock
    private EmployerRepository employerRepository;

    @Mock
    private UserDetailsRepository userDetailsRepository;

    private EmployerService employerService;

    private static final Long ID = 1L;

    private static final String EMAIL = "test@gmail.com";

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        this.employerService = new EmployerServiceImpl(employerRepository, userDetailsRepository);
    }

    @Test
    void saveEmployer_happyPath(){
        //arrange
        final var employer = Employer.builder()
                .id(ID)
                .build();
        when(employerRepository.save(employer)).thenReturn(employer);

        //act
        final var actual = employerService.saveEmployer(employer);

        //assert
        assertThat(actual).isEqualTo(employer);
    }

    @Test
    void getEmployerById_happyPath(){
        //arrange
      final var expected = Employer.builder()
              .id(ID)
              .build();
      when(employerRepository.findById(ID)).thenReturn(Optional.of(expected));

        //act
        final var actual = employerService.getEmployerById(ID);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getEmployerByEmail_happyPath(){
        //arrange
        final var expected = Employer.builder()
                .id(ID)
                .userDetails(UserDetails.builder().email(EMAIL).build())
                .build();
        when(employerRepository.findByUserDetailsEmail(EMAIL)).thenReturn(expected);

        //act
        final var actual = employerService.getEmployerByEmail(EMAIL);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getAllEmployers_happyPath(){
        //arrange
        final var employer1 = Employer.builder().id(ID).build();
        final var employer2 = Employer.builder().id(2L).build();
        final var expected = List.of(employer1, employer2);

        when(employerRepository.findAll()).thenReturn(List.of(employer1, employer2));

        //act
        final var actual = employerService.getAllEmployers();

        //assert
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    void updateEmployer_happyPath(){
        //arrange
        final var userDetails = UserDetails.builder()
                .id(ID)
                .build();
        final var employer = Employer.builder()
                .id(ID)
                .country("Germany")
                .userDetails(userDetails)
                .build();
        final var expected = employer.withCountry("Ukraine");

        when(employerRepository.findById(ID)).thenReturn(Optional.of(employer));
        when(userDetailsRepository.findById(ID)).thenReturn(Optional.of(userDetails));
        when(employerRepository.save(expected)).thenReturn(expected);

        //act
        final var actual = employerService.updateEmployer(expected, ID);

        //assert
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void deleteCandidate_happyPath(){
        //act
        employerService.deleteEmployer(ID);

        //assert
        verify(employerRepository).deleteById(ID);
    }
}

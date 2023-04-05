package ua.pp.juna.vacanciesservice.controller.employers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import ua.pp.juna.vacanciesservice.domain.UserDetails;
import ua.pp.juna.vacanciesservice.domain.employers.Employer;
import ua.pp.juna.vacanciesservice.service.employers.EmployerService;
import ua.pp.juna.vacanciesservice.utils.PhotoSaver;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;


public class EmployerControllerTest {
    private EmployerController employerController;

    @Mock
    private EmployerService employerService;

    @Mock
    private PhotoSaver photoSaver;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        this.employerController = new EmployerController(employerService, photoSaver);
    }

    private static final Long ID = 1L;

    @Test
    void saveEmployer_happyPath(){
        //arrange
        final var employer = Employer.builder()
                .id(ID).build();
        final var expected = ResponseEntity.status(HttpStatus.CREATED).body(employer);

        when(employerService.saveEmployer(employer)).thenReturn(employer);

        //act
        final var actual = employerController.saveEmployer(employer);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getAllEmployers_happyPath(){
        //arrange
        final var employers = List.of(
                Employer.builder().id(ID).build(),
                Employer.builder().id(2L).build(),
                Employer.builder().id(3L).build()
        );
        final var expected = ResponseEntity.ok().body(employers);
        when(employerService.getAllEmployers()).thenReturn(employers);

        //act
        final var actual = employerController.getAllEmployers();

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getEmployerById_happyPath(){
        //arrange
        final var employer = Employer.builder().id(ID).build();
        final var expected = ResponseEntity.ok().body(employer);
        when(employerService.getEmployerById(ID)).thenReturn(employer);

        //act
        final var actual = employerController.getEmployerById(ID);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getEmployerByEmail_happyPath(){
        //arrange
        final var email = "email";
        final var employer = Employer.builder().userDetails(UserDetails.builder().email(email).build()).build();
        final var expected = ResponseEntity.ok().body(employer);
        when(employerService.getEmployerByEmail(email)).thenReturn(employer);

        //act
        final var actual = employerController.getEmployerByEmail(email);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void updateEmployer_happyPath(){
        //arrange
        final var updatedEmployer = Employer.builder().id(ID).country("Germany").build();
        final var expected = ResponseEntity.ok().body(updatedEmployer);
        when(employerService.updateEmployer(updatedEmployer, ID)).thenReturn(updatedEmployer);

        //act
        final var actual = employerController.updateEmployer(updatedEmployer, ID);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void deleteEmployer_happyPath(){
        //arrange
        final var expected = ResponseEntity.ok().build();

        //act
        final var actual = employerController.deleteEmployer(ID);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void handleFileUpload_happyPath(){
        //arrange
        final var name = "name";
        final var file = new MockMultipartFile(name, (byte[]) null);
        final var folder = "employers";
        final var expected = ResponseEntity.ok().body(name);
        when(photoSaver.savePhoto(file, folder)).thenReturn(name);

        //act
        final var actual = employerController.handleFileUpload(file);

        //assert
        assertThat(actual).isEqualTo(expected);
    }
}

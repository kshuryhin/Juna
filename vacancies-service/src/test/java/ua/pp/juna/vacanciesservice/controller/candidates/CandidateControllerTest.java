package ua.pp.juna.vacanciesservice.controller.candidates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import ua.pp.juna.vacanciesservice.controller.candidates.models.ChangePasswordRequest;
import ua.pp.juna.vacanciesservice.domain.UserDetails;
import ua.pp.juna.vacanciesservice.domain.candidates.Candidate;
import ua.pp.juna.vacanciesservice.service.candidates.CandidateService;
import ua.pp.juna.vacanciesservice.utils.PhotoSaver;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

class CandidateControllerTest {
     private CandidateController candidateController;
     @Mock
     private CandidateService candidateService;
     @Mock
     private PhotoSaver photoSaver;

     private static final Long ID = 1L;

     @BeforeEach
     void setUp(){
         MockitoAnnotations.initMocks(this);
         candidateController = new CandidateController(candidateService, photoSaver);
     }

     @Test
     void saveCandidate_happyPath(){
          //arrange
          final var candidate = Candidate.builder()
                  .id(ID)
                  .country("Germany")
                  .build();
          final var expected = ResponseEntity.status(HttpStatus.CREATED).body(candidate);
          when(candidateService.saveCandidate(candidate)).thenReturn(candidate);

          //act
          final var actual = candidateController.saveCandidate(candidate);

          //assert
          assertThat(actual).isEqualTo(expected);
     }

     @Test
     void getAllCandidates_happyPath(){
          //arrange
          final var candidates = List.of(
                  Candidate.builder().id(ID).build(),
                  Candidate.builder().id(2L).build(),
                  Candidate.builder().id(3L).build()
          );
          final var expected = ResponseEntity.ok().body(candidates);
          when(candidateService.getAllCandidates()).thenReturn(candidates);

          //act
          final var actual = candidateController.getAllCandidates();

          //assert
          assertThat(actual).isEqualTo(expected);
     }

     @Test
     void getCandidateById_happyPath(){
          //arrange
          final var candidate = Candidate.builder().id(ID).build();
          final var expected = ResponseEntity.ok().body(candidate);
          when(candidateService.getCandidateById(ID)).thenReturn(candidate);

          //act
          final var actual = candidateController.getCandidateById(ID);

          //assert
          assertThat(actual).isEqualTo(expected);
     }

     @Test
     void getCandidateByEmail_happyPath(){
          //arrange
          final var email = "email";
          final var candidate = Candidate.builder().userDetails(UserDetails.builder().email(email).build()).build();
          final var expected = ResponseEntity.ok().body(candidate);
          when(candidateService.getCandidateByEmail(email)).thenReturn(candidate);

          //act
          final var actual = candidateController.getCandidateByEmail(email);

          //assert
          assertThat(actual).isEqualTo(expected);
     }

     @Test
     void updateCandidate_happyPath(){
          //arrange
          final var updatedCandidate = Candidate.builder().id(ID).country("Germany").build();
          final var expected = ResponseEntity.ok().body(updatedCandidate);
          when(candidateService.updateCandidate(updatedCandidate, ID)).thenReturn(updatedCandidate);

          //act
          final var actual = candidateController.updateCandidate(updatedCandidate, ID);

          //assert
          assertThat(actual).isEqualTo(expected);
     }

     @Test
     void deleteCandidate_happyPath(){
          //arrange
          final var expected = ResponseEntity.ok().build();

          //act
          final var actual = candidateController.deleteCandidate(ID);

          //assert
          assertThat(actual).isEqualTo(expected);
     }

     @Test
     void handleFileUpload_happyPath(){
          //arrange
          final var name = "name";
          final var file = new MockMultipartFile(name, (byte[]) null);
          final var expected = ResponseEntity.ok().body(name);
          when(photoSaver.savePhoto(file)).thenReturn(name);

          //act
          final var actual = candidateController.handleFileUpload(file);

          //assert
          assertThat(actual).isEqualTo(expected);
     }

     @Test
     void patchCandidate_happyPath(){
          //arrange
          final var newPassword = "12345";
          final var email = "ksurygin5@gmail.com";
          final var changePasswordRequest = ChangePasswordRequest.builder().newPassword(newPassword).email(email).build();
          final var candidate = Candidate.builder().userDetails(UserDetails.builder().password(newPassword).email(email).build()).build();
          final var expected = ResponseEntity.ok(candidate);

          when(candidateService.patchCandidate(email, newPassword)).thenReturn(candidate);

          //act
          final var actual = candidateController.patchCandidate(changePasswordRequest);

          //assert
          assertThat(actual).isEqualTo(expected);
     }
}

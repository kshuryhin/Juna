package ua.pp.juna.vacanciesservice.services.candidates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.pp.juna.vacanciesservice.domain.UserDetails;
import ua.pp.juna.vacanciesservice.domain.candidates.Candidate;
import ua.pp.juna.vacanciesservice.domain.vacancies.EnglishLevel;
import ua.pp.juna.vacanciesservice.domain.vacancies.Grade;
import ua.pp.juna.vacanciesservice.repo.CandidateRepository;
import ua.pp.juna.vacanciesservice.repo.UserDetailsRepository;
import ua.pp.juna.vacanciesservice.service.candidates.CandidateService;
import ua.pp.juna.vacanciesservice.service.candidates.CandidateServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CandidateServiceTest {
    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private UserDetailsRepository userDetailsRepository;

    private CandidateService candidateService;

    private static final Long ID = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        candidateService = new CandidateServiceImpl(candidateRepository, userDetailsRepository);
    }

    @Test
    void getCandidateById_happyPath() {
        //arrange
        final var expected = Candidate.builder()
                .id(ID)
                .country("Germany")
                .grade(Grade.JUNIOR)
                .englishLevel(EnglishLevel.NO_ENGLISH)
                .build();
        when(candidateRepository.findById(ID)).thenReturn(Optional.of(expected));

        //act
        final var actual = candidateService.getCandidateById(ID);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getCandidateByEmail_happyPath(){
        //assert
        final var email = "an5bro@gmail.com";
        final var expected = Candidate.builder()
                .userDetails(UserDetails.builder().email(email).build())
                .build();
        when(candidateRepository.findByUserDetailsEmail(email)).thenReturn(expected);

        //act
        final var actual = candidateService.getCandidateByEmail(email);

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getAllCandidates_happyPath(){
        //arrange
        final var expected = List.of(
                Candidate.builder().id(ID).build(),
                Candidate.builder().id(2L).build(),
                Candidate.builder().id(3L).build()
        );
        when(candidateRepository.findAll()).thenReturn(expected);

        //act
        final var actual = candidateService.getAllCandidates();

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void updateCandidate_happyPath(){
        //arrange
        final var userDetailsId = 2L;
        final var userDetails = UserDetails.builder().id(userDetailsId).build();
        final var candidateToUpdate = Candidate.builder()
                .id(ID)
                .userDetails(userDetails)
                .country("Germany")
                .build();
        final var existingCandidate = Candidate.builder()
                .id(ID)
                .userDetails(userDetails)
                .country("Ukraine")
                .build();
        when(candidateRepository.findById(ID)).thenReturn(Optional.of(existingCandidate));
        when(userDetailsRepository.findById(userDetailsId)).thenReturn(Optional.of(userDetails));
        when(candidateRepository.save(candidateToUpdate)).thenReturn(candidateToUpdate);

        //act
        final var actual = candidateService.updateCandidate(candidateToUpdate, ID);

        //assert
        assertThat(actual).isEqualTo(candidateToUpdate);
    }

    @Test
    void deleteCandidate_happyPath(){
        //act
        candidateService.deleteCandidate(ID);

        //assert
        verify(candidateRepository).deleteById(ID);
    }

    @Test
    void patchCandidate_happyPath(){
        //arrange
        final var email = "ksurygin5@gmail.com";
        final var newPassword = "12345";
        final var candidate = Candidate.builder()
                .userDetails(UserDetails.builder().email(email).build())
                .build();
        final var expected = Candidate.builder()
                .userDetails(UserDetails.builder().password(newPassword).email(email).build())
                .build();

        when(candidateRepository.findByUserDetailsEmail(email)).thenReturn(candidate);
        when(candidateRepository.save(expected)).thenReturn(expected);

        //act
        final var actual = candidateService.patchCandidate(email, newPassword);

        //assert
        assertThat(actual).isEqualTo(expected);
    }
}


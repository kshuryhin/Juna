package ua.pp.juna.vacanciesservice.services.candidates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.pp.juna.vacanciesservice.domain.UserDetails;
import ua.pp.juna.vacanciesservice.domain.candidates.Candidate;
import ua.pp.juna.vacanciesservice.domain.vacancies.Category;
import ua.pp.juna.vacanciesservice.domain.vacancies.EmploymentType;
import ua.pp.juna.vacanciesservice.domain.vacancies.EnglishLevel;
import ua.pp.juna.vacanciesservice.domain.vacancies.Grade;
import ua.pp.juna.vacanciesservice.repo.candidates.CandidateRepository;
import ua.pp.juna.vacanciesservice.repo.UserDetailsRepository;
import ua.pp.juna.vacanciesservice.service.candidates.CandidateService;
import ua.pp.juna.vacanciesservice.service.candidates.CandidateServiceImpl;
import ua.pp.juna.vacanciesservice.utils.Parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.pp.juna.vacanciesservice.domain.vacancies.Category.JAVA;

class CandidateServiceTest {
    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private UserDetailsRepository userDetailsRepository;

    private CandidateService candidateService;

    private static final Long ID = 1L;
    private static final String COUNTRY = "Germany";

    private static final Category CATEGORY = JAVA;
    private static final Integer SALARY_EXPECTATIONS = 2500;

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
        final var actual = candidateService.getAllCandidates(Map.of());

        //assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getAllCandidates_withParams_happyPath(){
        final var candidate1 = Candidate.builder()
                .id(ID)
                .country("Germany")
                .grade(Grade.JUNIOR)
                .employmentType(EmploymentType.REMOTE)
                .englishLevel(EnglishLevel.ADVANCED)
                .category(Category.JAVA)
                .salaryExpectations(1500)
                .build();
        final var candidate2  = Candidate.builder()
                .id(2L)
                .country("Ukraine")
                .grade(Grade.MIDDLE)
                .employmentType(EmploymentType.ALL)
                .englishLevel(EnglishLevel.BEGINNER)
                .category(Category.PHP)
                .salaryExpectations(900)
                .build();
        final var candidate3 = Candidate.builder()
                .id(3L)
                .country("Germany")
                .grade(Grade.JUNIOR)
                .employmentType(EmploymentType.REMOTE)
                .englishLevel(EnglishLevel.ADVANCED)
                .category(Category.JAVA)
                .salaryExpectations(2500)
                .build();
        final var candidate4 = Candidate.builder()
                .id(4L)
                .country("Germany")
                .grade(Grade.SENIOR)
                .employmentType(EmploymentType.ALL)
                .englishLevel(EnglishLevel.ADVANCED)
                .category(Category.JAVA)
                .salaryExpectations(2400)
                .build();
        final var candidates = List.of(candidate1, candidate2, candidate3, candidate4);
        final var expected = List.of(candidate1, candidate3, candidate4);
        final var map = new HashMap<Parameter, String>();
        map.put(Parameter.CATEGORY, CATEGORY.name());
        map.put(Parameter.COUNTRY, COUNTRY);
        map.put(Parameter.SALARY_EXPECTATIONS, String.valueOf(SALARY_EXPECTATIONS));
        map.put(Parameter.GRADE, null);
        map.put(Parameter.EMPLOYMENT, null);
        map.put(Parameter.ENGLISH_LEVEL, null);

        when(candidateRepository.findAll()).thenReturn(candidates);

        //act
        final var actual = candidateService.getAllCandidates(map);

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
}


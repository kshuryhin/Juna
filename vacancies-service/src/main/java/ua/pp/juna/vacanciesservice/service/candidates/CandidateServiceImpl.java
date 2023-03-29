package ua.pp.juna.vacanciesservice.service.candidates;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.pp.juna.vacanciesservice.domain.candidates.Candidate;
import ua.pp.juna.vacanciesservice.repo.CandidateRepository;
import ua.pp.juna.vacanciesservice.repo.UserDetailsRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final UserDetailsRepository userDetailsRepository;
    @Override
    public Candidate saveCandidate(final Candidate candidate) {
        log.info("Saving candidate: {}", candidate);

        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate getCandidateById(Long id) {
        log.info("Fetching candidate with id {}", id);
        return candidateRepository.findById(id).orElse(null);
    }

    @Override
    public Candidate getCandidateByEmail(String email) {
        log.info("Fetching candidate with email {}", email);
        return candidateRepository.findByUserDetailsEmail(email);
    }

    @Override
    public List<Candidate> getAllCandidates() {
        log.info("Fetching all candidates");
        return candidateRepository.findAll();
    }

    @Override
    public Candidate updateCandidate(Candidate candidate, Long id) {
        log.info("Updating candidate {}", candidate);
        final var updatedCandidate = candidateRepository.findById(id).get()
                .withPosition(candidate.getPosition())
                .withSalaryExpectations(candidate.getSalaryExpectations())
                .withGrade(candidate.getGrade())
                .withCountry(candidate.getCountry())
                .withPhoneNumber(candidate.getPhoneNumber())
                .withCity(candidate.getCity())
                .withSkills(candidate.getSkills())
                .withCategory(candidate.getCategory())
                .withEnglishLevel(candidate.getEnglishLevel())
                .withEmploymentType(candidate.getEmploymentType())
                .withWorkExperience(candidate.getWorkExperience())
                .withPetProjectsDescription(candidate.getPetProjectsDescription())
                .withPhotoLink(candidate.getPhotoLink());

        final var updatedUserDetails = userDetailsRepository.findById(updatedCandidate.getUserDetails().getId()).get()
                .withEmail(candidate.getUserDetails().getEmail())
                .withFirstName(candidate.getUserDetails().getFirstName())
                .withLastName(candidate.getUserDetails().getLastName());


        return candidateRepository.save(updatedCandidate.withUserDetails(updatedUserDetails));
    }

    @Override
    public Candidate patchCandidate(String email, String newPassword) {
        final var candidate = candidateRepository.findByUserDetailsEmail(email);
        log.info("Patching candidate {} with new password", candidate);
        candidate.getUserDetails().setPassword(newPassword);
        return candidateRepository.save(candidate);
    }

    @Override
    public void deleteCandidate(Long id) {
        log.info("Deleting candidate with id {}", id);
        candidateRepository.deleteById(id);
    }
}

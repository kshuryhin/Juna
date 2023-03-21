package ua.pp.juna.mentorservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.pp.juna.mentorservice.model.Mentor;
import ua.pp.juna.mentorservice.repo.MentorRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class MentorServiceImpl implements MentorService{
    private final MentorRepository mentorRepository;

    @Override
    public Mentor addMentor(Mentor mentor) {
        log.info("Adding mentor {}", mentor.getId());
        return mentorRepository.save(mentor);
    }

    @Override
    public Mentor getMentorById(Long id) {
        log.info("Getting mentor with id {}", id);
        return mentorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Mentor> getAllMentors() {
        log.info("Getting ALL mentors");
        return mentorRepository.findAll();
    }

    @Override
    public Mentor updateMentor(Mentor mentor, Long id) {
        log.info("Updating mentor with id {}", id);
        Mentor updated = mentorRepository.findById(id).orElse(null);

        if (updated == null) {
            return null;
        }


        updated.setDescription(mentor.getDescription());
        updated.setFirstName(mentor.getFirstName());
        updated.setLastName(mentor.getLastName());

        return mentorRepository.save(updated);
    }

    @Override
    public String deleteMentor(Long id) {
        log.info("Deleting mentor with id {}", id);
        try {
            mentorRepository.deleteById(id);
            return "Deleted successfully";
        } catch (Exception e) {
            return "Could not delete mentor with id " + id;
        }
    }
}

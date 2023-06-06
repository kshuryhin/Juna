package ua.pp.juna.mentorservice.service;

import ua.pp.juna.mentorservice.model.Mentor;

import java.util.List;

public interface MentorService {
    Mentor addMentor(Mentor mentor);
    Mentor getMentorById(Long id);
    Mentor getMentorByEmail(String email);
    List<Mentor> getAllMentors();
    Mentor updateMentor(Mentor mentor, Long id);
    boolean deleteMentor(Long id);
}

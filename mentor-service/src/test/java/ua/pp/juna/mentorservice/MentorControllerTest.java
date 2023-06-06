package ua.pp.juna.mentorservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.pp.juna.mentorservice.controller.MentorController;
import ua.pp.juna.mentorservice.model.CreateMentorRequest;
import ua.pp.juna.mentorservice.model.Mentor;
import ua.pp.juna.mentorservice.repo.MentorRepository;
import ua.pp.juna.mentorservice.service.MentorService;
import ua.pp.juna.mentorservice.utils.PhotoSaver;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class MentorControllerTest {

    @Mock
    private MentorService mentorService;

    @Mock
    private PhotoSaver photoSaver;

    @Mock
    private MentorRepository mentorRepository;

    @InjectMocks
    private MentorController mentorController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetMentorById() {
        Long id = 1L;
        Mentor mentor = new Mentor();
        when(mentorService.getMentorById(id)).thenReturn(mentor);

        ResponseEntity<Mentor> response = mentorController.getMentorById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mentor, response.getBody());
        verify(mentorService, times(1)).getMentorById(id);
    }

    @Test
    public void testGetMentorByEmail() {
        String email = "test@example.com";
        Mentor mentor = new Mentor();
        when(mentorRepository.findByEmail(email)).thenReturn(mentor);

        ResponseEntity<Mentor> response = mentorController.getMentorByEmail(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mentor, response.getBody());
        verify(mentorRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testGetAllMentors() {
        List<Mentor> mentors = Collections.singletonList(new Mentor());
        when(mentorService.getAllMentors()).thenReturn(mentors);

        ResponseEntity<List<Mentor>> response = mentorController.getAllMentors();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mentors, response.getBody());
        verify(mentorService, times(1)).getAllMentors();
    }

    @Test
    public void testDeleteMentor() {
        Long id = 1L;
        when(mentorService.deleteMentor(id)).thenReturn(true);

        ResponseEntity<String> response = mentorController.deleteMentor(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted successfully!", response.getBody());
        verify(mentorService, times(1)).deleteMentor(id);
    }

    @Test
    public void testUpdateMentor() {
        Long id = 1L;
        Mentor mentor = new Mentor();
        when(mentorService.updateMentor(mentor, id)).thenReturn(mentor);

        ResponseEntity<Mentor> response = mentorController.updateMentor(id, mentor);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mentor, response.getBody());
        verify(mentorService, times(1)).updateMentor(mentor, id);
    }

    // Add more test cases for other methods if needed

    // ...
}
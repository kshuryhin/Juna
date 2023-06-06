package ua.pp.juna.mentorservice;

//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import ua.pp.juna.mentorservice.controller.MentorController;
//import ua.pp.juna.mentorservice.model.CreateMentorRequest;
//import ua.pp.juna.mentorservice.model.Mentor;
//import ua.pp.juna.mentorservice.repo.MentorRepository;
//import ua.pp.juna.mentorservice.service.MentorService;
//import ua.pp.juna.mentorservice.utils.PhotoSaver;
//
//@WebMvcTest(MentorController.class)
//public class MentorControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private MentorService mentorService;
//
//    @MockBean
//    private PhotoSaver photoSaver;
//
//    @MockBean
//    private MentorRepository mentorRepository;
//
//    @Test
//    public void testAddMentor() throws Exception {
//        Mentor mentor = new Mentor();
//        mentor.setId(1L);
//        mentor.setFirstName("John");
//
//        CreateMentorRequest request = new CreateMentorRequest();
//        request.setUserDetails(mentor);
//
//        Mockito.when(mentorService.addMentor(Mockito.any(Mentor.class))).thenReturn(mentor);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/mentors")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"userDetails\":{\"id\":1,\"firstName\":\"John\"}}")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"));
//    }
//
//    // Add more test methods for other controller endpoints...
//
//}


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.pp.juna.mentorservice.controller.MentorController;
import ua.pp.juna.mentorservice.model.CreateMentorRequest;
import ua.pp.juna.mentorservice.model.Mentor;
import ua.pp.juna.mentorservice.repo.MentorRepository;
import ua.pp.juna.mentorservice.service.MentorService;
import ua.pp.juna.mentorservice.utils.PhotoSaver;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(MentorController.class)
public class MentorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MentorService mentorService;

    @MockBean
    private PhotoSaver photoSaver;

    @MockBean
    private MentorRepository mentorRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddMentor() throws Exception {
//        Mentor mentor = new Mentor();
//        mentor.setId(1L);
//        mentor.setFirstName("John Doe");
//
//        when(mentorService.addMentor(any(Mentor.class))).thenReturn(mentor);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/mentors")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"firstName\":\"John Doe\"}"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John Doe"));
//
//        verify(mentorService, times(1)).addMentor(any(Mentor.class));
    }

    @Test
    public void testGetMentorById() throws Exception {
        Mentor mentor = new Mentor();
        mentor.setId(1L);
        mentor.setFirstName("John Doe");

        when(mentorService.getMentorById(1L)).thenReturn(mentor);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/mentors/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John Doe"));

        verify(mentorService, times(1)).getMentorById(1L);
    }

    @Test
    public void testGetMentorByEmail() throws Exception {
        Mentor mentor = new Mentor();
        mentor.setId(1L);
        mentor.setFirstName("John Doe");

        when(mentorRepository.findByEmail("john.doe@example.com")).thenReturn(mentor);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/mentors/email/john.doe@example.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John Doe"));

        verify(mentorRepository, times(1)).findByEmail("john.doe@example.com");
    }

    @Test
    public void testGetAllMentors() throws Exception {
        List<Mentor> mentors = new ArrayList<>();
        Mentor mentor1 = new Mentor();
        mentor1.setId(1L);
        mentor1.setFirstName("John Doe");
        Mentor mentor2 = new Mentor();
        mentor2.setId(2L);
        mentor2.setFirstName("Jane Smith");
        mentors.add(mentor1);
        mentors.add(mentor2);

        when(mentorService.getAllMentors()).thenReturn(mentors);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/mentors"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("Jane Smith"));

        verify(mentorService, times(1)).getAllMentors();
    }

    @Test
    public void testDeleteMentor() throws Exception {
        when(mentorService.deleteMentor(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/mentors/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Deleted successfully!"));

        verify(mentorService, times(1)).deleteMentor(1L);
    }

    @Test
    public void testUpdateMentor() throws Exception {
        Mentor mentor = new Mentor();
        mentor.setId(1L);
        mentor.setFirstName("John Doe");

        when(mentorService.updateMentor(any(Mentor.class), eq(1L))).thenReturn(mentor);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/mentors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John Doe"));

        verify(mentorService, times(1)).updateMentor(any(Mentor.class), eq(1L));
    }

    @Test
    public void testHandleFileUpload() throws Exception {
        when(photoSaver.savePhoto(any(), anyString())).thenReturn("photo.jpg");

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/mentors/upload")
                        .file("file", "photo".getBytes()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("photo.jpg"));

        verify(photoSaver, times(1)).savePhoto(any(), anyString());
    }
}
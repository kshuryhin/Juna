package ua.pp.juna.mentorservice.service;

import ua.pp.juna.mentorservice.model.Lesson;

import java.util.List;

public interface LessonService {
    Lesson addLesson(Lesson lesson);
    Lesson getLessonById(Long id);
    List<Lesson> getAllLessons();
    String deleteLesson(Long id);
    Lesson updateLesson(Lesson lesson, Long id);
}

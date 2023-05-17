package ua.pp.juna.mentorservice.service;

import ua.pp.juna.mentorservice.model.Course;
import ua.pp.juna.mentorservice.model.Lesson;

import java.util.List;

public interface LessonService {
    Course addLesson(Lesson lesson, Long courseId);
    Lesson getLessonById(Long id);
    List<Lesson> getAllLessons();
    void deleteLesson(Long courseId, Integer orderInCourse);
    Lesson updateLesson(Lesson lesson, Long courseId, Integer orderInCourse);

    Lesson getLessonByOrderInCourse(Long courseId, Integer orderInCourse);

    boolean deleteById(Long id);
}

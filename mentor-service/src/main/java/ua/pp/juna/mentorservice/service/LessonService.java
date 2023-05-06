package ua.pp.juna.mentorservice.service;

import ua.pp.juna.mentorservice.model.Course;
import ua.pp.juna.mentorservice.model.Lesson;

import java.util.List;

public interface LessonService {
    Course addLesson(Lesson lesson, Long courseId);
    Lesson getLessonById(Long id);
    List<Lesson> getAllLessons();
    boolean deleteLesson(Long id);
    Lesson updateLesson(Lesson lesson, Long id);

    Lesson getLessonByOrderInCourse(Long courseId, Integer orderInCourse);
}

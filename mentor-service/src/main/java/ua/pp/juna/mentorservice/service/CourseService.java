package ua.pp.juna.mentorservice.service;

import ua.pp.juna.mentorservice.model.Course;

import java.util.List;

public interface CourseService {
    Course addCourse(Course course, Long mentorId);
    Course getCourseById(Long id);
    List<Course> getAllCourses();
    String deleteCourse(Long id);
    Course updateCourse(Course course, Long id);
}
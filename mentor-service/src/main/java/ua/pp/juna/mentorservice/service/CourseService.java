package ua.pp.juna.mentorservice.service;

import ua.pp.juna.mentorservice.model.Course;
import ua.pp.juna.mentorservice.model.Mentor;

import java.util.List;

public interface CourseService {
    Mentor addCourse(Course course, Long mentorId);
    Course getCourseById(Long id);
    List<Course> getAllCourses();
    boolean deleteCourse(Long id);
    Course updateCourse(Course course, Long id);
}

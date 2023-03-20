package ua.pp.juna.mentorservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.pp.juna.mentorservice.model.Course;
import ua.pp.juna.mentorservice.repo.CourseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService{
    private final CourseRepository courseRepository;

    @Override
    public Course addCourse(Course course) {
        log.info("Adding course with id {}", course.getId());
        return courseRepository.save(course);
    }

    @Override
    public Course getCourseById(Long id) {
        log.info("Getting course with id {}", id);

        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public List<Course> getAllCourses() {
        log.info("Getting all courses");
        return courseRepository.findAll();
    }

    @Override
    public String deleteCourse(Long id) {
        log.info("Deleting course with id {}", id);
        try {
            courseRepository.deleteById(id);
            return "Deleted successfully";
        } catch (Exception e) {
            return "Could not delete course with id " + id;
        }
    }

    @Override
    public Course updateCourse(Course course, Long id) {
        log.info("Updating course with id {}", id);
        Course updated  = courseRepository.findById(id).orElse(null);

        if (updated == null) {
            return null;
        }

        updated.setStudents(course.getStudents());
        updated.setLessons(course.getLessons());

        return courseRepository.save(updated);
    }
}

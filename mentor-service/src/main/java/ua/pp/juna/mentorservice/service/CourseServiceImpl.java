package ua.pp.juna.mentorservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.pp.juna.mentorservice.model.Course;
import ua.pp.juna.mentorservice.model.Mentor;
import ua.pp.juna.mentorservice.model.Student;
import ua.pp.juna.mentorservice.repo.CourseRepository;
import ua.pp.juna.mentorservice.repo.MentorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService{
    private final CourseRepository courseRepository;
    private final MentorRepository mentorRepository;

    @Override
    public Course addCourse(final Course course, final Long mentorId) {
        log.info("Adding course with id {}", course.getId());
        final Mentor mentor = mentorRepository.findById(mentorId).orElse(null);
        if (mentor == null)
            return null;


        Course result = courseRepository.save(course);
        mentor.getCourses().add(course);
        mentorRepository.save(mentor);
        return result;
    }

    @Override
    public Course getCourseById(final Long id) {
        log.info("Getting course with id {}", id);

        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public List<Course> getAllCourses() {
        log.info("Getting all courses");
        return courseRepository.findAll();
    }

    @Override
    public boolean deleteCourse(final Long id) {
        log.info("Deleting course with id {}", id);
        try {
            courseRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Course updateCourse(final Course course, final Long id) {
        log.info("Updating course with id {}", id);
        final Course updated  = courseRepository.findById(id).orElse(null);

        if (updated == null) {
            return null;
        }

        updated.setStudents(course.getStudents());
        updated.setName(course.getName());

        return courseRepository.save(updated);
    }
}

package ua.pp.juna.mentorservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.pp.juna.mentorservice.model.Course;
import ua.pp.juna.mentorservice.model.Lesson;
import ua.pp.juna.mentorservice.repo.CourseRepository;
import ua.pp.juna.mentorservice.repo.LessonRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class LessonServiceImpl implements LessonService{
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Override
    public Course addLesson(final Lesson lesson, final Long courseId) {
        log.info("Adding lesson with id {}", lesson.getId());
        final Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null)
            throw new EntityNotFoundException("Cannot find course with id" + courseId);

        course.getLessons().add(lesson);

        return courseRepository.save(course);
    }

    @Override
    public Lesson getLessonById(final Long id) {
        log.info("Getting lesson with id {}", id);
        return lessonRepository.findById(id).orElse(null);
    }

    @Override
    public List<Lesson> getAllLessons() {
        log.info("Getting all lessons");
        return lessonRepository.findAll();
    }

    @Override
    public boolean deleteLesson(final Long id) {
        log.info("Deleting lesson with id {}", id);
        try {
            lessonRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Lesson updateLesson(final Lesson lesson, final Long id) {
        final Lesson updated = lessonRepository.findById(id).orElse(null);
        if (updated == null) {
            return null;
        }

        updated.setVideoLinks(lesson.getVideoLinks());
        updated.setText(lesson.getText());
        updated.setName(lesson.getName());

        return lessonRepository.save(updated);
    }
}

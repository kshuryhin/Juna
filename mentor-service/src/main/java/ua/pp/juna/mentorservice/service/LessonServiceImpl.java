package ua.pp.juna.mentorservice.service;

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
    public Lesson addLesson(Lesson lesson, Long courseId) {
        log.info("Adding lesson with id {}", lesson.getId());
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null)
            return null;

        lesson.setCourse(course);

        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson getLessonById(Long id) {
        log.info("Getting lesson with id {}", id);
        return lessonRepository.findById(id).orElse(null);
    }

    @Override
    public List<Lesson> getAllLessons() {
        log.info("Getting all lessons");
        return lessonRepository.findAll();
    }

    @Override
    public String deleteLesson(Long id) {
        log.info("Deleting lesson with id {}", id);
        try {
            lessonRepository.deleteById(id);
            return "Deleted successfully!";
        } catch (Exception e) {
            return "Could not delete lesson with id " + id;
        }
    }

    @Override
    public Lesson updateLesson(Lesson lesson, Long id) {
        Lesson updated = lessonRepository.findById(id).orElse(null);
        if (updated == null) {
            return null;
        }

        updated.setVideoLinks(lesson.getVideoLinks());
        updated.setText(lesson.getText());

        return lessonRepository.save(updated);
    }
}

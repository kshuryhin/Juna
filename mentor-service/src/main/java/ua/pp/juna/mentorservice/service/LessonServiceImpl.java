package ua.pp.juna.mentorservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

        final int order = course.getLessons().size();
        lesson.setOrderInCourse(order + 1);
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
    public boolean deleteById(Long id) {
        try {
            lessonRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("Could not delete lesson {}", id, e);
            return false;
        }
    }

    @Override
    public Lesson updateLesson(final Lesson lesson, final Long courseId, Integer orderInCourse) {
        final Lesson updated = getLessonByOrderInCourse(courseId, orderInCourse);
        if (updated == null) {
            return null;
        }

        updated.setVideoLinks(lesson.getVideoLinks());
        updated.setText(lesson.getText());
        updated.setName(lesson.getName());
        updated.setOrderInCourse(lesson.getOrderInCourse());

        return lessonRepository.save(updated);
    }

    @Override
    public Lesson getLessonByOrderInCourse(Long courseId, Integer orderInCourse) {
        final Course course = courseRepository.findById(courseId).orElse(null);

        List<Lesson> lessons = course.getLessons();

        return lessons.stream().filter(lesson -> lesson.getOrderInCourse() == orderInCourse).findFirst().orElse(null);
    }

}

package ua.pp.juna.mentorservice.controller;


import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.pp.juna.mentorservice.model.Course;
import ua.pp.juna.mentorservice.model.Lesson;
import ua.pp.juna.mentorservice.model.Mentor;
import ua.pp.juna.mentorservice.model.Student;
import ua.pp.juna.mentorservice.repo.CourseRepository;
import ua.pp.juna.mentorservice.repo.LessonRepository;
import ua.pp.juna.mentorservice.repo.StudentRepository;
import ua.pp.juna.mentorservice.service.CourseService;

import java.util.List;
//@CrossOrigin(origins = {"http://localhost:8085", "http://localhost:4200"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final CourseService courseService;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;

    @PostMapping("/{mentorId}")
    public ResponseEntity<Mentor> addCourse(@RequestBody Course course, @PathVariable Long mentorId) {
        return ResponseEntity.ok().body(courseService.addCourse(course, mentorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        final Course result =  courseService.getCourseById(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(result);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok().body(courseService.getAllCourses());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        final boolean isDeleted = courseService.deleteCourse(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body("Deleted successfully!");
        }
    }

    @GetMapping("/student/email/{email}")
    public ResponseEntity<List<Course>> getCoursesByStudent(@PathVariable String email) {

        final Student student = studentRepository.findByEmail(email);
        if (student == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(courseRepository.findAllByStudents(student));
        }
    }

    @GetMapping("/isApplied/{courseId}/email/{email}")
    public ResponseEntity<Boolean> isCourseApplied(@PathVariable(name = "courseId") Long courseId,
                                                   @PathVariable(name = "email") String email) {

        Course course = courseService.getCourseById(courseId);
        Student student = studentRepository.findByEmail(email);
        return ResponseEntity.ok().body(course.getStudents().contains(student));
    }

    @GetMapping("/isLiked/{courseId}/email/{email}")
    public ResponseEntity<Boolean> isCourseLiked(@PathVariable(name = "courseId") Long courseId,
                                                 @PathVariable(name = "email") String email) {
        Course course = courseService.getCourseById(courseId);
        Student student = studentRepository.findByEmail(email);
        return ResponseEntity.ok().body(course.getStudentsLikedIds().contains(student.getId()));
    }

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<Course> getCourseByLesson(@PathVariable Long lessonId) {
        final Lesson lesson = lessonRepository.findById(lessonId).orElse(null);
        return ResponseEntity.ok().body(courseRepository.findByLessons(lesson));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course, @PathVariable Long id){
        final Course result = courseService.updateCourse(course, id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(result);
        }
    }
}

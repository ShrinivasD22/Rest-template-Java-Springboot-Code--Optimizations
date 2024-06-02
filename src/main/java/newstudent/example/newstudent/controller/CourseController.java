package newstudent.example.newstudent.controller;


import newstudent.example.newstudent.entity.Course;
import newstudent.example.newstudent.studentservice.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/student/{studentId}")
    public ResponseEntity<Course> saveOrUpdateCourse(@PathVariable int studentId, @RequestBody Course course) {
        Course savedCourse = courseService.saveOrUpdateCourse(studentId, course);
        return ResponseEntity.ok(savedCourse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable int id) {
        Optional<Course> course = courseService.getCourseById(id);
        return course.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //get the all courses by student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Course>> getCoursesByStudentId(@PathVariable int studentId) {
        List<Course> courses = courseService.getCoursesByStudentId(studentId);
        return ResponseEntity.ok(courses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourseById(@PathVariable int id) {
        courseService.deleteCourseById(id);
        return ResponseEntity.ok("Course deleted successfully with ID: " + id);
    }
}


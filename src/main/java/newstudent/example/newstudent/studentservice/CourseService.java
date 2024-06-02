package newstudent.example.newstudent.studentservice;



import newstudent.example.newstudent.entity.Course;
import newstudent.example.newstudent.entity.Student;
import newstudent.example.newstudent.repository.CourseRepo;
import newstudent.example.newstudent.repository.StudentRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepo courseRepo; 
    
    @Autowired
    private StudentRepo studentRepo; 

    public Course saveOrUpdateCourse(int studentId, Course course) {
        Optional<Student> studentOptional = studentRepo.findById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            course.setStudent(student); // Associate course with the specified student
            return courseRepo.save(course);
        } else {
            throw new IllegalArgumentException("Student not found with ID: " + studentId);
        }
    }
    public Optional<Course> getCourseById(int id) {
        return courseRepo.findById(id);
    }

    public List<Course> getCoursesByStudentId(int studentId) {
        return courseRepo.findByStudentId(studentId);
    }

    public void deleteCourseById(int id) {
        courseRepo.deleteById(id);
    }
}


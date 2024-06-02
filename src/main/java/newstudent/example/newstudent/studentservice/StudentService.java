package newstudent.example.newstudent.studentservice;

import java.util.List; 

import newstudent.example.newstudent.entity.School;
import newstudent.example.newstudent.entity.Student;
import newstudent.example.newstudent.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo; 
    
    @Autowired
    private SchoolService schoolService;

    // Method to create or update a student
    public Student saveOrUpdateStudent(Student student) {
        return studentRepo.save(student);
    }

    // Method to get a student by ID
    public Optional<Student> getStudentById(int id) {
        return studentRepo.findById(id);
    }

    // Method to get all students
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    // Method to delete a student by ID
    public int deleteStudentById(int id) {
        studentRepo.deleteById(id);
		return id;
    }

    // Method to find students by roll number
    public List<Student> getStudentsByRollno(String rollno) {
        return studentRepo.findByRollno(rollno);
    } 
    
    public Student assignSchoolToStudent(int studentId, int schoolId) {
        Optional<Student> studentOpt = studentRepo.findById(studentId);
        Optional<School> schoolOpt = schoolService.getSchoolById(schoolId);

        if (studentOpt.isPresent() && schoolOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setSchool(schoolOpt.get());
            return studentRepo.save(student);
        } else {
            throw new RuntimeException("Student or School not found");
        }
    } 
    
    public List<Object[]> findAllStudentsWithSchoolNames() {
        return studentRepo.findAllStudentsWithSchoolNames();
    }
    
}

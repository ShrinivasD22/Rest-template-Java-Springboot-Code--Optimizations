package newstudent.example.newstudent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import newstudent.example.newstudent.entity.Student;



public interface StudentRepo extends JpaRepository<Student,Integer>{ 
	List<Student> findByRollno(String rollno);
	 @Query("SELECT s.firstname, s.lastname, s.rollno, s.classname, sc.name FROM Student s LEFT JOIN s.school sc")
	    List<Object[]> findAllStudentsWithSchoolNames();
}
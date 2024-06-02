package newstudent.example.newstudent.repository;



import newstudent.example.newstudent.entity.Course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepo extends JpaRepository<Course, Integer> {
	List<Course> findByStudentId(int studentId);
}


package newstudent.example.newstudent.repository;



import newstudent.example.newstudent.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepo extends JpaRepository<School, Integer> {
}


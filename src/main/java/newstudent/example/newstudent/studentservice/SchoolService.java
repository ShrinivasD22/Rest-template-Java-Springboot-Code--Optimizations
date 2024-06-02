package newstudent.example.newstudent.studentservice;



import newstudent.example.newstudent.entity.School;
import newstudent.example.newstudent.repository.SchoolRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepo schoolRepo;

    public School saveOrUpdateSchool(School school) {
        return schoolRepo.save(school);
    }

    public Optional<School> getSchoolById(int id) {
        return schoolRepo.findById(id);
    }

    public void deleteSchoolById(int id) {
        schoolRepo.deleteById(id);
    }
}

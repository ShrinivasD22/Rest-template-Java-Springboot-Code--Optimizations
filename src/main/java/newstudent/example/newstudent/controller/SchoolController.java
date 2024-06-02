package newstudent.example.newstudent.controller;



import newstudent.example.newstudent.entity.School;
import newstudent.example.newstudent.studentservice.SchoolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/schools")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @PostMapping
    public ResponseEntity<School> saveOrUpdateSchool(@RequestBody School school) {
        School savedSchool = schoolService.saveOrUpdateSchool(school);
        return ResponseEntity.ok(savedSchool);
    }

    @GetMapping("/{id}")
    public ResponseEntity<School> getSchoolById(@PathVariable int id) {
        Optional<School> school = schoolService.getSchoolById(id);
        return school.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchoolById(@PathVariable int id) {
        schoolService.deleteSchoolById(id);
        return ResponseEntity.ok("School deleted successfully with ID: " + id);
    }
}


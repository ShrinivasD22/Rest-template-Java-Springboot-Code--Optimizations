package newstudent.example.newstudent.controller;
import org.springframework.http.MediaType;





import newstudent.example.newstudent.entity.Student;
import newstudent.example.newstudent.entity.UserDTO;
import newstudent.example.newstudent.studentservice.FileService;
import newstudent.example.newstudent.studentservice.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService; 
    
    @Autowired
	private FileService fileservice;   
    
    @Autowired
    private RestTemplate restTemplate; 
    
  
    
    
    @Value("${project.image}")
	private String path;

    // Create or update a student
    @PostMapping("/addstudent")
    public ResponseEntity<Student> saveOrUpdateStudent(@RequestBody Student student) {
        Student savedStudent = studentService.saveOrUpdateStudent(student);
        return ResponseEntity.ok(savedStudent);
    }  
    
    
    
    // Update an existing student
    @PutMapping("/updatestudent/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student studentDetails) {
        Optional<Student> existingStudent = studentService.getStudentById(id);
        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();
            student.setFirstname(studentDetails.getFirstname());
            student.setLastname(studentDetails.getLastname());
            student.setRollno(studentDetails.getRollno());
            student.setClassname(studentDetails.getClassname());
            Student updatedStudent = studentService.saveOrUpdateStudent(student);
            return ResponseEntity.ok(updatedStudent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    

//    // Get a student by ID
//    @GetMapping("/getstudent/{id}")
//    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
//        Optional<Student> student = studentService.getStudentById(id);
//        
//        
//        //rest temp api code call
//
//        
//        
//        return student.map(ResponseEntity::ok)
//                      .orElseGet(() -> ResponseEntity.notFound().build());
//    }

    // Get a student by ID
    @GetMapping("/getstudent/{id}")
    public ResponseEntity<Map<String, Object>> getStudentById(@PathVariable int id) {
        Optional<Student> studentOpt = studentService.getStudentById(id);

        if (studentOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Student student = studentOpt.get();

        // RestTemplate API call
        Map<String, Object> userMap = this.restTemplate.getForObject("http://localhost:9090/api/users/serialize/1", Map.class);

        // Create the response map
        Map<String, Object> response = new HashMap<>();
        response.put("student", student);
        response.put("user", userMap);

        return ResponseEntity.ok(response);
    }

    
    
    
    //BY using UserDto class
//    @GetMapping("/getstudent/{id}")
//    public ResponseEntity<Map<String, Object>> getStudentById(@PathVariable int id) {
//        Optional<Student> studentOpt = studentService.getStudentById(id);
//
//        if (studentOpt.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Student student = studentOpt.get();
//
//        // RestTemplate API call
//        UserDTO user = this.restTemplate.getForObject("http://localhost:9090/api/users/serialize/1", UserDTO.class);
//
//        // Create the response map
//        Map<String, Object> response = new HashMap<>();
//        response.put("student", student);
//        response.put("user", user);
//
//        return ResponseEntity.ok(response);
//    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // Get all students
    @GetMapping("/getallstudent")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // Delete a student by ID
    @DeleteMapping("deletestudent/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable int id) {
        studentService.deleteStudentById(id);
//        return ResponseEntity.noContent().build(); 
        int deletedId = studentService.deleteStudentById(id);
        return ResponseEntity.ok("Student deleted successfully with ID: " + deletedId);
    }

    // Get students by roll number
    @GetMapping("/rollno/{rollno}")
    public ResponseEntity<List<Student>> getStudentsByRollno(@PathVariable String rollno) {
        List<Student> students = studentService.getStudentsByRollno(rollno);
        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(students);
        }
    } 
    
    @PutMapping("/{studentId}/assign-school/{schoolId}")
    public ResponseEntity<Student> assignSchoolToStudent(@PathVariable int studentId, @PathVariable int schoolId) {
        Student updatedStudent = studentService.assignSchoolToStudent(studentId, schoolId);
        return ResponseEntity.ok(updatedStudent);
    } 
    
    @GetMapping("/with-school-names")
    public ResponseEntity<List<Object[]>> getAllStudentsWithSchoolNames() {
        List<Object[]> studentsWithSchoolNames = studentService.findAllStudentsWithSchoolNames();
        return ResponseEntity.ok(studentsWithSchoolNames);
    } 
    

  

      
       

        @PostMapping("/image/upload/{studentId}")
        public ResponseEntity<Student> uploadStudentImage(@RequestParam("image") MultipartFile image,
                                                          @PathVariable Integer studentId) throws IOException {
            Student student = studentService.getStudentById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            // Upload image and get file name
            String fileName = fileservice.uploadImage(path, image);

            // Set image file name in the student entity
            student.setImageName(fileName);

            // Save the updated student entity
            Student updatedStudent = studentService.saveOrUpdateStudent(student);

            return ResponseEntity.ok(updatedStudent);
        }  
        
        
        //methode to serve files
        
        @GetMapping(value= "/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
        public void downloadImage(
       	@PathVariable("imageName") String imageName,
       	HttpServletResponse response	 
       		 ) throws IOException{
       	 InputStream resource = this.fileservice.getResource(path, imageName);
       	 response.setContentType(MediaType.IMAGE_JPEG_VALUE);
       	 StreamUtils.copy(resource,response.getOutputStream());
        }

}


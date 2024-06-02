package newstudent.example.newstudent.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter

public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String firstname;
	private String lastname;
	private String rollno;
	private String classname; 
	
	@Column
	private String imageName;
 
	
	 @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	 @JsonIgnore
	    private List<Course> courses; 
	 
	    @OneToOne
	    @JoinColumn(name = "school_id")
	    @JsonIgnore
	    private School school; 
	    
//	  List<User> user=new ArrayList();
   
}

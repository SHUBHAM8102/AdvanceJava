package pom.capgemini.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String department;

    @OneToOne(cascade = CascadeType.ALL)
    private InstructorProfile instructorProfile;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();

    public Instructor() {}

    public Instructor(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public InstructorProfile getInstructorProfile() { return instructorProfile; }
    public void setInstructorProfile(InstructorProfile instructorProfile) { this.instructorProfile = instructorProfile; }
    public List<Course> getCourses() { return courses; }
    public void setCourses(List<Course> courses) { this.courses = courses; }
}


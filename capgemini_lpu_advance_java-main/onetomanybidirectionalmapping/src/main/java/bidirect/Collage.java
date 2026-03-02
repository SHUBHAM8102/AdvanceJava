package bidirect;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "collage")
public class Collage {
	
	@Id
	private int id;
	private String ename;
	private String location;
	private String pincode;
	
	@OneToMany(mappedBy="collage", cascade = CascadeType.ALL)
	private List<Student> student;

	public Collage(int id, String ename, String location, String pincode) {
		super();
		this.id = id;
		this.ename = ename;
		this.location = location;
		this.pincode = pincode;
	}
	
	
	public Collage() {}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getEname() {
		return ename;
	}


	public void setEname(String ename) {
		this.ename = ename;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getPincode() {
		return pincode;
	}


	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public List<Student> getStudent() {
		return student;
	}


	public void setStudent(List<Student> student) {
		this.student = student;
	}


	@Override
	public String toString() {
		return "Collage [id=" + id + ", ename=" + ename + ", location=" + location + ", pincode=" + pincode
				+ ", student=" + student + "]";
	}	
	
}

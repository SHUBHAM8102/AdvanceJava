package bidirect;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
public class Student {
	
	@Id
	private int id;
	private String ename;
	private String branch;
	
	@ManyToOne
	@JoinColumn(name="college_id")
	private Collage collage;

	
	public Collage getCollage() {
		return collage;
	}


	public void setCollage(Collage collage) {
		this.collage = collage;
	}


	public Student(int id, String ename, String branch) {
		super();
		this.id = id;
		this.ename = ename;
		this.branch = branch;
	}
	
	
	public Student() {}


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


	public String getBranch() {
		return branch;
	}


	public void setBranch(String branch) {
		this.branch = branch;
	}


	@Override
	public String toString() {
		return "Student [id=" + id + ", ename=" + ename + ", branch=" + branch + "]";
	}
	
	
	
	
	
}

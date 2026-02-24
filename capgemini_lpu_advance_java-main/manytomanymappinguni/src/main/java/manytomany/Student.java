package manytomany;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
public class Student {
	
	@Id
	private int id;
	private String ename;
	private String branch;
	private String gender;
	
	@ManyToMany
	private List<Subject> subject;
	
	
	
	public List<Subject> getSubject() {
		return subject;
	}


	public void setSubject(List<Subject> subject) {
		this.subject = subject;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public Student(int id, String ename, String branch, String gender) {
		super();
		this.id = id;
		this.ename = ename;
		this.branch = branch;
		this.gender = gender;
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

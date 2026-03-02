package bidirect;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "passport")
public class Passport {
	@Id
	private int id;
	private String name;
	private String dob;
	private String citizen;
	
	@OneToOne(mappedBy = "passport")
	private Person person;
	
	
	public Passport() {}


	public Passport(int id, String name, String dob, String citizen) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.citizen = citizen;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDob() {
		return dob;
	}


	public void setDob(String dob) {
		this.dob = dob;
	}


	public String getCitizen() {
		return citizen;
	}


	public void setCitizen(String citizen) {
		this.citizen = citizen;
	}


	public Person getPerson() {
		return person;
	}


	public void setPerson(Person person) {
		this.person = person;
	}


	@Override
	public String toString() {
		return "Passport [id=" + id + ", name=" + name + ", dob=" + dob + ", citizen=" + citizen + ", person=" + person
				+ "]";
	}
	
	
	
	
	
}

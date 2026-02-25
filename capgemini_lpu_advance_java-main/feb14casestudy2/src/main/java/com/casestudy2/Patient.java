package com.casestudy2;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "patient")
public class Patient {
	
	@Id
	private Long id;
	private String ename;
	private int age;
	private String contact;
	
	@OneToOne
	private MedicalRecord medicalRecord;

	public Patient(Long id, String ename, int age, String contact) {
		super();
		this.id = id;
		this.ename = ename;
		this.age = age;
		this.contact = contact;
	}
	
	
	public Patient() {}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getEname() {
		return ename;
	}


	public void setEname(String ename) {
		this.ename = ename;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public String getContact() {
		return contact;
	}


	public void setContact(String contact) {
		this.contact = contact;
	}

	

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}


	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}


	@Override
	public String toString() {
		return "Patient [id=" + id + ", ename=" + ename + ", age=" + age + ", contact=" + contact + ", medicalRecord="
				+ medicalRecord + "]";
	}
	
	
	
	
	
}

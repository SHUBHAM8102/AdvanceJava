package com.casestudy2;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointment")
public class Appointment {
	@Id
	private Long id;
	private String visitDate;
	private double fees;
	
	@ManyToOne
	private Patient patient;

	public Appointment(Long id, String visitDate, double fees) {
		super();
		this.id = id;
		this.visitDate = visitDate;
		this.fees = fees;
	}
	
	
	public Appointment() {}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getVisitDate() {
		return visitDate;
	}


	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}


	public double getFees() {
		return fees;
	}


	public void setFees(double fees) {
		this.fees = fees;
	}


	public Patient getPatient() {
		return patient;
	}


	public void setPatient(Patient patient) {
		this.patient = patient;
	}


	@Override
	public String toString() {
		return "Appointment [id=" + id + ", visitDate=" + visitDate + ", fees=" + fees + ", patient=" + patient + "]";
	}
	
	
	
	
	
	
	
	
}

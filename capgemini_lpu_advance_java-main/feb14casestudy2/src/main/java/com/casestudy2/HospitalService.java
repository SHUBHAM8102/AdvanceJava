package com.casestudy2;

import java.util.*;

public class HospitalService {
	
	AppointmentDao ad = new AppointmentDao();
	DoctorDao dd = new DoctorDao();
	PatientDao pd = new PatientDao();
	
	public void registerPatientWithMedicalRecord(Patient patient, MedicalRecord medicalrecord) {
		MedicalRecordDao mr = new MedicalRecordDao();
		mr.saveMedicalRecord(medicalrecord);
		pd.savePatient(patient);
		patient.setMedicalRecord(medicalrecord);
	}
	
	public void addMultipleAppointmentsToDoctor(Long d_id, List<Appointment> list) {
		Doctor d = dd.findDoctor(d_id);
	    if(d != null){

	        for(Appointment a : list){
	            ad.saveAppointment(a);
	        }

	        d.setList(list);
	        dd.saveDoctor(d);   
	    }

	}
	
	public void assignAppointmentToPatient(Long appointmentId, Long patientId) {
	    Appointment a = ad.findAppointment(appointmentId);
	    Patient p = pd.findPatient(patientId);

	    if(a != null && p != null){
	        a.setPatient(p);
	        ad.saveAppointment(a); 
	        System.out.println("Appointment assigned to patient");
	    }
	    else{
	        System.out.println("Patient or Appointment not found");
	    }
	}
	
	public void updateAppointmentFee(Long appointmentId, double newFee) {
		Appointment a = ad.findAppointment(appointmentId);
		if(a != null) {
			a.setFees(newFee);
			ad.saveAppointment(a);
		}
	}
	
	public Doctor fetchDoctorsWithAppointments(Long doctorId) {
		Doctor d = dd.findDoctor(doctorId);
		if(d != null) {
			return d;
		}
		return null;
	}
	
	public void deletePatient(Long patientId){

	    Patient p = pd.findPatient(patientId);

	    if(p != null){

	        pd.deletePatient(patientId);

	        System.out.println("Patient deleted");
	    }
	    else{
	        System.out.println("Patient not found");
	    }
	}

}

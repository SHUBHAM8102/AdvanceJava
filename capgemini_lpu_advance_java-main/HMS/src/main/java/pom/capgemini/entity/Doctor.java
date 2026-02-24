package pom.capgemini.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "specialization", nullable = false)
    private String specialization;

    @Column(name = "license_no", unique = true, nullable = false)
    private String licenseNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private List<Appointment> appointments = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "patient_doctors",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    private List<Patient> patients = new ArrayList<>();

    public Doctor() {
    }

    public Doctor(String name, String specialization, String licenseNo) {
        this.name = name;
        this.specialization = specialization;
        this.licenseNo = licenseNo;
    }

    public Doctor(String name, String specialization, String licenseNo, Department department) {
        this.name = name;
        this.specialization = specialization;
        this.licenseNo = licenseNo;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public void addAppointment(Appointment appointment) {
        if (!this.appointments.contains(appointment)) {
            this.appointments.add(appointment);
        }
    }

    public void removeAppointment(Appointment appointment) {
        this.appointments.remove(appointment);
    }

    public void addPatient(Patient patient) {
        if (!this.patients.contains(patient)) {
            this.patients.add(patient);
            patient.getDoctors().add(this);
        }
    }

    public void removePatient(Patient patient) {
        if (this.patients.contains(patient)) {
            this.patients.remove(patient);
            patient.getDoctors().remove(this);
        }
    }

    public List<Appointment> getAppointmentList() {
        return new ArrayList<>(appointments);
    }

    public List<Patient> getPatientList() {
        return new ArrayList<>(patients);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", licenseNo='" + licenseNo + '\'' +
                ", department=" + (department != null ? department.getName() : "None") +
                ", appointmentCount=" + appointments.size() +
                ", patientCount=" + patients.size() +
                '}';
    }
}


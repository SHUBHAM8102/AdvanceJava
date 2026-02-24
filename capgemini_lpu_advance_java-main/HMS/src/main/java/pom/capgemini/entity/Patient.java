package pom.capgemini.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "blood_group")
    private String bloodGroup;

    @Column(name = "phone")
    private String phone;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "med_record_id", unique = true)
    private MedicalRecord medicalRecord;

    @ManyToMany(mappedBy = "patients", fetch = FetchType.LAZY)
    private List<Doctor> doctors = new ArrayList<>();

    public Patient() {
    }

    public Patient(String name, LocalDate dob, String bloodGroup, String phone) {
        this.name = name;
        this.dob = dob;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public void addDoctor(Doctor doctor) {
        if (!this.doctors.contains(doctor)) {
            this.doctors.add(doctor);
            doctor.getPatients().add(this);
        }
    }

    public void removeDoctor(Doctor doctor) {
        if (this.doctors.contains(doctor)) {
            this.doctors.remove(doctor);
            doctor.getPatients().remove(this);
        }
    }

    public int getAge() {
        return Period.between(dob, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", age=" + getAge() +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}





package pom.capgemini.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "medicines", nullable = false)
    private String medicines;

    @Column(name = "dosage", nullable = false)
    private String dosage;

    @Column(name = "issued_date", nullable = false)
    private LocalDate issuedDate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    public Prescription() {
    }

    public Prescription(String medicines, String dosage, LocalDate issuedDate) {
        this.medicines = medicines;
        this.dosage = dosage;
        this.issuedDate = issuedDate;
        this.isActive = true;
    }

    public Prescription(String medicines, String dosage, LocalDate issuedDate, boolean isActive) {
        this.medicines = medicines;
        this.dosage = dosage;
        this.issuedDate = issuedDate;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean checkActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "id=" + id +
                ", medicines='" + medicines + '\'' +
                ", dosage='" + dosage + '\'' +
                ", issuedDate=" + issuedDate +
                ", isActive=" + isActive +
                '}';
    }
}


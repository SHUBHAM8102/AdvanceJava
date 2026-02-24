package pom.capgemini.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "appoint_date", nullable = false)
    private LocalDateTime appointDate;

    @Column(name = "status", nullable = false)
    private String status = "SCHEDULED";

    @Column(name = "reason")
    private String reason;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "prescription_id", unique = true)
    private Prescription prescription;

    public Appointment() {
    }

    public Appointment(LocalDateTime appointDate, String status, String reason) {
        this.appointDate = appointDate;
        this.status = status;
        this.reason = reason;
    }

    public Appointment(LocalDateTime appointDate, String reason) {
        this.appointDate = appointDate;
        this.reason = reason;
        this.status = "SCHEDULED";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getAppointDate() {
        return appointDate;
    }

    public void setAppointDate(LocalDateTime appointDate) {
        this.appointDate = appointDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public boolean hasPrescription() {
        return prescription != null;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", appointDate=" + appointDate +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                ", hasPrescription=" + hasPrescription() +
                '}';
    }
}


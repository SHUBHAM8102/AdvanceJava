package pom.capgemini.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "leads")
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String contactInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private SalesEmployee employee;

    @Column(nullable = false)
    private boolean converted = false;

    // Constructors
    public Lead() {
    }

    public Lead(String name, String source, String contactInfo) {
        this.name = name;
        this.source = source;
        this.contactInfo = contactInfo;
    }

    // Getters and Setters
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public SalesEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(SalesEmployee employee) {
        this.employee = employee;
    }

    public boolean isConverted() {
        return converted;
    }

    public void setConverted(boolean converted) {
        this.converted = converted;
    }

    @Override
    public String toString() {
        return "Lead{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", source='" + source + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", converted=" + converted +
                '}';
    }
}


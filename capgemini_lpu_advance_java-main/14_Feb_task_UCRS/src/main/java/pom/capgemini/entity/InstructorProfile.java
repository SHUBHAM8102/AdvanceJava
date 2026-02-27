package pom.capgemini.entity;

import jakarta.persistence.*;

@Entity
public class InstructorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String officeRoom;
    private String phone;

    public InstructorProfile() {}

    public InstructorProfile(String officeRoom, String phone) {
        this.officeRoom = officeRoom;
        this.phone = phone;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getOfficeRoom() { return officeRoom; }
    public void setOfficeRoom(String officeRoom) { this.officeRoom = officeRoom; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}


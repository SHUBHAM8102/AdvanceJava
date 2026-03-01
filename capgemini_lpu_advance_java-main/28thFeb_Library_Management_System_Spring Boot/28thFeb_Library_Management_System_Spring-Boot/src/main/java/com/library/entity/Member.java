package com.library.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Member {
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long memberId;
	private String name;
	private String email;
	private String phone;
	private LocalDate memberSince;
	private String status;
	
	@OneToMany(mappedBy = "member")
    private List<Loan> loans = new ArrayList<>();

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDate getMemberSince() {
		return memberSince;
	}

	public void setMemberSince(LocalDate memberSince) {
		this.memberSince = memberSince;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	@Override
	public String toString() {
		return "Member [memberId=" + memberId + ", name=" + name + ", email=" + email + ", phone=" + phone
				+ ", memberSince=" + memberSince + ", status=" + status + ", loans=" + loans + "]";
	}
	
	

}

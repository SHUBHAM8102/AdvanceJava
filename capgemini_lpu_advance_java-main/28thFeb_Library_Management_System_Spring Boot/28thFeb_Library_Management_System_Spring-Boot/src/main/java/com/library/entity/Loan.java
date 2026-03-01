package com.library.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Loan {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long loanId;
	    private LocalDate issueDate;
	    private LocalDate dueDate;
	    private LocalDate returnDate;
	    private String loanStatus;
	    @ManyToOne
	    @JoinColumn(name = "member_id")
	    private Member member;

	    @ManyToOne
	    @JoinColumn(name = "book_id")
	    private Book book;
	    
		public Member getMember() {
			return member;
		}
		public void setMember(Member member) {
			this.member = member;
		}
		public Book getBook() {
			return book;
		}
		public void setBook(Book book) {
			this.book = book;
		}
		public Long getLoanId() {
			return loanId;
		}
		public void setLoanId(Long loanId) {
			this.loanId = loanId;
		}
		public LocalDate getIssueDate() {
			return issueDate;
		}
		public void setIssueDate(LocalDate issueDate) {
			this.issueDate = issueDate;
		}
		public LocalDate getDueDate() {
			return dueDate;
		}
		public void setDueDate(LocalDate dueDate) {
			this.dueDate = dueDate;
		}
		public LocalDate getReturnDate() {
			return returnDate;
		}
		public void setReturnDate(LocalDate returnDate) {
			this.returnDate = returnDate;
		}
		public String getLoanStatus() {
			return loanStatus;
		}
		public void setLoanStatus(String loanStatus) {
			this.loanStatus = loanStatus;
		}
		@Override
		public String toString() {
			return "Loan [loanId=" + loanId + ", issueDate=" + issueDate + ", dueDate=" + dueDate + ", returnDate="
					+ returnDate + ", loanStatus=" + loanStatus + ", member=" + member + ", book=" + book + "]";
		}
		
	    

}

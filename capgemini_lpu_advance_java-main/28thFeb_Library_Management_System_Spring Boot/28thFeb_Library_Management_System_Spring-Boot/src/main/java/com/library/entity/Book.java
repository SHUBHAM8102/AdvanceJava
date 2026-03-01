package com.library.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Book {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long bookId;
    private String title; 
    private String isbn; 
    private int publishYear; 
    private int copiesTotal; 
    private int copiesAvailable;
    private String status; 
    
    @ManyToMany
    @JoinTable(name="bookAuthor",joinColumns=@JoinColumn(name="bookId"),inverseJoinColumns = @JoinColumn(name = "authorId"))
    private Set<Author> author=new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name="categoryId")
    private Category category;
    
    @ManyToOne
    @JoinColumn(name="branchId")
    private LibraryBranch branch;
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Loan> loans = new ArrayList<>();

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getPublishYear() {
		return publishYear;
	}

	public void setPublishYear(int publishYear) {
		this.publishYear = publishYear;
	}

	public int getCopiesTotal() {
		return copiesTotal;
	}

	public void setCopiesTotal(int copiesTotal) {
		this.copiesTotal = copiesTotal;
	}

	public int getCopiesAvailable() {
		return copiesAvailable;
	}

	public void setCopiesAvailable(int copiesAvailable) {
		this.copiesAvailable = copiesAvailable;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Author> getAuthor() {
		return author;
	}

	public void setAuthor(Set<Author> author) {
		this.author = author;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public LibraryBranch getBranch() {
		return branch;
	}

	public void setBranch(LibraryBranch branch) {
		this.branch = branch;
	}

	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", title=" + title + ", isbn=" + isbn + ", publishYear=" + publishYear
				+ ", copiesTotal=" + copiesTotal + ", copiesAvailable=" + copiesAvailable + ", status=" + status
				+ ", author=" + author + ", category=" + category + ", branch=" + branch + ", loans=" + loans + "]";
	}
    
    
    


}

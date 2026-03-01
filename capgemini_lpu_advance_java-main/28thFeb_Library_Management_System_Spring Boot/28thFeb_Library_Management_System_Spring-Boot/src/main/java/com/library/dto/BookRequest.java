package com.library.dto;

import java.util.Set;

import com.library.entity.Book;

public class BookRequest {

    private Book bookDetails;
    private Long categoryId;
    private Long branchId;
    private Set<Long> authorIds;
    
    public Book getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(Book bookDetails) {
        this.bookDetails = bookDetails;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Set<Long> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(Set<Long> authorIds) {
        this.authorIds = authorIds;
    }
}
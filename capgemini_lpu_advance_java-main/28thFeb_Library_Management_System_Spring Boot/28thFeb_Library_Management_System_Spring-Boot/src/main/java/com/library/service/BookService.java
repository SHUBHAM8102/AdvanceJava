package com.library.service;

import java.util.List;
import java.util.Set;

import com.library.entity.Book;

public interface BookService {
	Book addBook(Book bookDetails, Long categoryId, Long branchId, Set<Long> authorIds);
	Book getBookById(Long bookId);
	List<Book> getAllBooks();
	Book updateBook(Long bookId, Book updatedDetails);
	Book deleteOrDisableBook(Long bookId);

}

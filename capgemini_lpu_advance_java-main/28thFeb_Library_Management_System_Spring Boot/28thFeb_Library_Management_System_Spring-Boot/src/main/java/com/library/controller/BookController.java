package com.library.controller;



import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.dto.BookRequest;
import com.library.entity.Book;
import com.library.service.BookService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@PostMapping
	public Book addBook(@RequestBody BookRequest request) {
		return bookService.addBook(request.getBookDetails(), request.getCategoryId(), request.getBranchId(),
				request.getAuthorIds());
	}

	@GetMapping("/{id}")
	public Book getBook(@PathVariable Long id) {
		return bookService.getBookById(id);
	}

	@GetMapping
	public List<Book> getAllBooks() {
		return bookService.getAllBooks();
	}

	@PutMapping("/{id}")
	public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
		return bookService.updateBook(id, book);
	}

	@DeleteMapping("/{id}")
	public String deleteBook(@PathVariable Long id) {
		bookService.deleteOrDisableBook(id);
		return "Book disabled successfully";
	}
}
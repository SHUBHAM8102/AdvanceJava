package com.library.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.Category;
import com.library.entity.LibraryBranch;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import com.library.repository.CategoryRepository;
import com.library.repository.LibraryBranchRepository;

@Service
public class BookServiceImplementation implements BookService {
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private LibraryBranchRepository branchRepository;
	@Autowired
	private AuthorRepository authorRepository;

	

	@Override
	public Book addBook(Book bookDetails, Long categoryId, Long branchId, Set<Long> authorIds) {
		// TODO Auto-generated method stub
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new RuntimeException("Category not found exception"));
		LibraryBranch branch = branchRepository.findById(branchId)
				.orElseThrow(() -> new RuntimeException("Branch not found"));
		 Set<Author> authors = new HashSet<>();

		    for (Long authorId : authorIds) {
		        Author author = authorRepository.findById(authorId)
		                .orElseThrow(() -> new RuntimeException("Author not found with id " + authorId));
		        authors.add(author);
		    }

		bookDetails.setCategory(category);
		bookDetails.setBranch(branch);
		bookDetails.setAuthor(authors);
		return bookRepository.save(bookDetails);
	}

	@Override
	public Book getBookById(Long bookId) {

		return bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book Not found"));
	}

	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Book updateBook(Long bookId, Book updatedDetails) {
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
		book.setTitle(updatedDetails.getTitle());
		book.setIsbn(updatedDetails.getIsbn());
		book.setCopiesAvailable(updatedDetails.getCopiesAvailable());
		book.setPublishYear(updatedDetails.getPublishYear());
		book.setStatus(updatedDetails.getStatus());
		return bookRepository.save(book);
	}

	@Override
	public Book deleteOrDisableBook(Long bookId) {
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
		bookRepository.delete(book);
		// book.setStatus("false");//we can do this also in place of deleting book
		return book;
	}

}

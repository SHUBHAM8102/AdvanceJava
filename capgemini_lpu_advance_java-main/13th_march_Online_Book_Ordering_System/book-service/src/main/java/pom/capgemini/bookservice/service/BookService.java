package pom.capgemini.bookservice.service;

import pom.capgemini.bookservice.entity.Book;
import pom.capgemini.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    public Book updateBook(Long bookId, Book bookDetails) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            Book existingBook = book.get();
            if (bookDetails.getTitle() != null) {
                existingBook.setTitle(bookDetails.getTitle());
            }
            if (bookDetails.getAuthor() != null) {
                existingBook.setAuthor(bookDetails.getAuthor());
            }
            if (bookDetails.getPrice() != null) {
                existingBook.setPrice(bookDetails.getPrice());
            }
            if (bookDetails.getStock() != null) {
                existingBook.setStock(bookDetails.getStock());
            }
            return bookRepository.save(existingBook);
        }
        return null;
    }

    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public void reduceStock(Long bookId, Integer quantity) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            Book existingBook = book.get();
            existingBook.setStock(existingBook.getStock() - quantity);
            bookRepository.save(existingBook);
        }
    }
}


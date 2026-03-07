package com.capgemini._march_city_Library.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini._march_city_Library.entity.Book;
import com.capgemini._march_city_Library.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book saveBook(Book book){
        return bookRepository.save(book);
    }

    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }
}
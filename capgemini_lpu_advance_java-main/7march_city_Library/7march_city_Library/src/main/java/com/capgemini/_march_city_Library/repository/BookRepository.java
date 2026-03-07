package com.capgemini._march_city_Library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini._march_city_Library.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAvailable(boolean available);

}
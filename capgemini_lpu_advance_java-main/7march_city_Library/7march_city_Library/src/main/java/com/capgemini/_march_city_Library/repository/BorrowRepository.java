package com.capgemini._march_city_Library.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini._march_city_Library.entity.BorrowRecord;

public interface BorrowRepository extends JpaRepository<BorrowRecord, Long> {

}
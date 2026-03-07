package com.capgemini._march_city_Library.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini._march_city_Library.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
package com.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.entity.LibraryBranch;

public interface LibraryBranchRepository extends JpaRepository<LibraryBranch, Long>{

}

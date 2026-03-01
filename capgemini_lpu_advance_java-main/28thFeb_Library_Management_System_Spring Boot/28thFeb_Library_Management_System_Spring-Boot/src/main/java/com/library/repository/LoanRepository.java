package com.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {
	List<Loan> findByMemberMemberId(Long memberId);
    
}

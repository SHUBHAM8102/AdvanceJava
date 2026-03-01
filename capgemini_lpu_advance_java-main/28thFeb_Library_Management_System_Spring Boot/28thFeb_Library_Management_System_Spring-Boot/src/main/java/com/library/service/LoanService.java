package com.library.service;

import java.time.LocalDate;
import java.util.List;

import com.library.entity.Loan;

public interface LoanService {
	Loan issueBook(Long memberId,Long bookId,LocalDate dueDate);
	Loan returnBook(Long loanId);
	Loan  getLoanById(Long loanId);
	List<Loan> getLoansByMember(Long memberId);
	List<Loan>getAllLoans();

}

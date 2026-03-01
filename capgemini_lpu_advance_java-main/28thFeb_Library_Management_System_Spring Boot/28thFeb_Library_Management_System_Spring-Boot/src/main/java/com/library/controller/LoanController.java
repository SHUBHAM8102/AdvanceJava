package com.library.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.entity.Loan;
import com.library.service.LoanService;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

	private final LoanService loanService;

	public LoanController(LoanService loanService) {
		this.loanService = loanService;
	}

	@PostMapping("/issue")
	public Loan issueBook(@RequestParam Long memberId, @RequestParam Long bookId, @RequestParam String dueDate) {

		return loanService.issueBook(memberId, bookId, LocalDate.parse(dueDate));
	}

	@PutMapping("/{loanId}/return")
	public Loan returnBook(@PathVariable Long loanId) {
		return loanService.returnBook(loanId);
	}

	@GetMapping("/{loanId}")
	public Loan getLoan(@PathVariable Long loanId) {
		return loanService.getLoanById(loanId);
	}

	@GetMapping
	public List<Loan> getAllLoans() {
		return loanService.getAllLoans();
	}

	@GetMapping("/member/{memberId}")
	public List<Loan> getLoansByMember(@PathVariable Long memberId) {
		return loanService.getLoansByMember(memberId);
	}
}

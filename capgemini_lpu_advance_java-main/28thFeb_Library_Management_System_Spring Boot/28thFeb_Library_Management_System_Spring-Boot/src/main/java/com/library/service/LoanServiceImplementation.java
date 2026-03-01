package com.library.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.entity.Book;
import com.library.entity.Loan;
import com.library.entity.Member;
import com.library.repository.BookRepository;
import com.library.repository.LoanRepository;
import com.library.repository.MemberRepository;

import jakarta.transaction.Transactional;

@Service
public class LoanServiceImplementation implements LoanService{
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private LoanRepository loanRepository;
	@Override
	@Transactional
	public Loan issueBook(Long memberId, Long bookId, LocalDate dueDate) {
		Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        if (book.getCopiesAvailable() <= 0) {
            throw new RuntimeException("No copies available");
        }

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setMember(member);
        loan.setIssueDate(LocalDate.now());
        loan.setDueDate(dueDate);
        loan.setLoanStatus("ISSUED");

        book.setCopiesAvailable(book.getCopiesAvailable() - 1);

        bookRepository.save(book);
        return loanRepository.save(loan);
	}
	@Override
	public Loan returnBook(Long loanId) {
		Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setReturnDate(LocalDate.now());
        loan.setLoanStatus("RETURNED");

        Book book = loan.getBook();
        book.setCopiesAvailable(book.getCopiesAvailable() + 1);

        bookRepository.save(book);
        return loanRepository.save(loan);
	}
	@Override
	public Loan getLoanById(Long loanId) {
		return loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found"));
	}
	@Override
	public List<Loan> getLoansByMember(Long memberId) {
	    return loanRepository.findByMemberMemberId(memberId);
	}
	@Override
	public List<Loan> getAllLoans() {
		return loanRepository.findAll();
	}
	
	
	
	
	
	
}

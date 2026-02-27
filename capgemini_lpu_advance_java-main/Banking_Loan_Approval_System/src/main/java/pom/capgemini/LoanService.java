package pom.capgemini;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

@Component
public class LoanService {
    private LoanValidator loanValidator;
    private AuditService auditService;

    public LoanService(@Qualifier("incomeValidator") LoanValidator loanValidator) {
        this.loanValidator = loanValidator;
    }

    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    public boolean approveLoan(double amount) {
        boolean isValid = loanValidator.validateLoan(amount);
        if (auditService != null) {
            auditService.audit("Loan application for amount: " + amount + " - " + (isValid ? "Approved" : "Rejected"));
        }
        return isValid;
    }
}


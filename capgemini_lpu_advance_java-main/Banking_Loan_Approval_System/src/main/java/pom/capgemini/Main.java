package pom.capgemini;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BankAppConfig.class);

        LoanService loanService = context.getBean(LoanService.class);
        AuditService auditService = context.getBean(AuditService.class);

        loanService.setAuditService(auditService);

        System.out.println("Testing Loan Approval System:");
        System.out.println("Loan 1 (300000): " + loanService.approveLoan(300000));
        System.out.println("Loan 2 (600000): " + loanService.approveLoan(600000));

        context.close();
    }
}
package pom.capgemini;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PaymentProcessor {

    private final PaymentService paymentService;

    @Autowired
    private TransactionLogger transactionLogger;

    public PaymentProcessor(@Qualifier("upiPayment") PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void processTransaction(double amount) {
        System.out.println("\n=== Processing Transaction ===");
        transactionLogger.logTransaction("Starting payment transaction for $" + amount);
        paymentService.processPayment(amount);
        transactionLogger.logTransaction("Payment transaction completed for $" + amount);
        System.out.println("=== Transaction Complete ===\n");
    }
}


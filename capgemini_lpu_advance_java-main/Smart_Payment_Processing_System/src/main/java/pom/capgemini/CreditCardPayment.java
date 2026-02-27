package pom.capgemini;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@Lazy
public class CreditCardPayment implements PaymentService {

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing Credit Card Payment of $" + amount);
        System.out.println("Initializing payment gateway connection...");
    }
}


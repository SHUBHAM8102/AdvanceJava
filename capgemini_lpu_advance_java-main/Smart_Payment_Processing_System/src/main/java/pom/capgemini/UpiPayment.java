package pom.capgemini;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class UpiPayment implements PaymentService {

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing UPI Payment of $" + amount);
        System.out.println("Initializing UPI gateway connection...");
    }
}


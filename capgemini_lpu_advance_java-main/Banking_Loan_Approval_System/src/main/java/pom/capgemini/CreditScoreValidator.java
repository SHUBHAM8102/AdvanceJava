package pom.capgemini;

import org.springframework.stereotype.Component;

@Component
@org.springframework.context.annotation.Primary
public class CreditScoreValidator implements LoanValidator {
    @Override
    public boolean validateLoan(double amount) {
        return amount <= 1000000;
    }
}


package pom.capgemini;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

@Component
@Scope("prototype")
public class IncomeValidator implements LoanValidator {
    @Override
    public boolean validateLoan(double amount) {
        return amount <= 500000;
    }
}


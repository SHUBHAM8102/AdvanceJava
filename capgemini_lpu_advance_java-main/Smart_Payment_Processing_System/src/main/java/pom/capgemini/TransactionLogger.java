package pom.capgemini;

import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class TransactionLogger {

    @PostConstruct
    public void init() {
        System.out.println("Logger initialized");
    }

    public void logTransaction(String message) {
        System.out.println("[TRANSACTION LOG] " + message);
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Logger destroyed");
    }
}


package pom.capgemini;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Lazy;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
@Lazy
public class AuditService {
    @PostConstruct
    public void init() {
        System.out.println("AuditService initialized");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("AuditService destroyed");
    }

    public void audit(String message) {
        System.out.println("Audit: " + message);
    }
}


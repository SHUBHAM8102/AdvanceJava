package pom.capgemini;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class DeliveryService {

    @PostConstruct
    public void init() {
        System.out.println("Delivery Service Ready");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Delivery Service Closed");
    }

    public void deliverOrder() {
        System.out.println("Order is being delivered");
    }
}


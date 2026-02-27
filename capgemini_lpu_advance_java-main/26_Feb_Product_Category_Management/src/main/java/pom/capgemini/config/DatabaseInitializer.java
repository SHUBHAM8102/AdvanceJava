package pom.capgemini.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n===========================================");
        System.out.println("Product Category Management System");
        System.out.println("Database initialized successfully!");
        System.out.println("Server running on: http://localhost:8088");
        System.out.println("===========================================\n");
    }
}


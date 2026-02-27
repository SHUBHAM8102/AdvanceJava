package pom.capgemini;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        System.out.println("Smart Payment Processing System");


        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("\n--- Spring Context Initialized ---\n");

        PaymentProcessor processor = context.getBean(PaymentProcessor.class);

        processor.processTransaction(100.50);
        processor.processTransaction(250.75);
        processor.processTransaction(50.00);

        System.out.println("\n--- Closing Spring Context ---\n");

        if (context instanceof AnnotationConfigApplicationContext) {
            ((AnnotationConfigApplicationContext) context).close();
        }

        System.out.println("\n========================================");
        System.out.println("Application Terminated");
        System.out.println("========================================");
    }
}


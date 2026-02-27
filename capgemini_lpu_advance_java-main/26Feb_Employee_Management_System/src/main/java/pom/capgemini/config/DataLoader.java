package pom.capgemini.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pom.capgemini.entity.Employee;
import pom.capgemini.repository.EmployeeRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;

    public DataLoader(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (employeeRepository.count() == 0) {
            employeeRepository.save(new Employee(null, "John Doe", "john@example.com", 50000.0, "IT"));
            employeeRepository.save(new Employee(null, "Jane Smith", "jane@example.com", 60000.0, "HR"));
            employeeRepository.save(new Employee(null, "Mike Johnson", "mike@example.com", 55000.0, "IT"));
            employeeRepository.save(new Employee(null, "Sarah Williams", "sarah@example.com", 65000.0, "Finance"));
            employeeRepository.save(new Employee(null, "Tom Brown", "tom@example.com", 52000.0, "IT"));
        }
    }
}


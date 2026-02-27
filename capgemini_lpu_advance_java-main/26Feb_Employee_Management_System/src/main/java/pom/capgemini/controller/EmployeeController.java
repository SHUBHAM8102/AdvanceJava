package pom.capgemini.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pom.capgemini.entity.Employee;
import pom.capgemini.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(employee));
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @GetMapping("/department/{name}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable String name) {
        return ResponseEntity.ok(employeeService.getEmployeesByDepartment(name));
    }

    @GetMapping("/highsalary")
    public ResponseEntity<List<Employee>> getEmployeesWithHighSalary(@RequestParam Double salary) {
        return ResponseEntity.ok(employeeService.getEmployeesWithHighSalary(salary));
    }

    @GetMapping("/sort")
    public ResponseEntity<List<Employee>> getEmployeesSortedBySalary() {
        return ResponseEntity.ok(employeeService.getEmployeesSortedBySalary());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Employee>> getEmployeesWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        Sort.Direction sortDirection = Sort.Direction.valueOf(direction.toUpperCase());
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        return ResponseEntity.ok(employeeService.getEmployeesWithPagination(pageable));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countEmployees() {
        return ResponseEntity.ok(employeeService.countEmployees());
    }

    @GetMapping("/salary-above-average")
    public ResponseEntity<List<Employee>> getEmployeesWithSalaryAboveAverage() {
        return ResponseEntity.ok(employeeService.getEmployeesWithSalaryAboveAverage());
    }
}


package pom.capgemini.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import pom.capgemini.entity.Lead;
import pom.capgemini.entity.SalesEmployee;
import java.util.List;

public class ReportService {

    private EntityManager entityManager;

    public ReportService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Get employee performance metrics
     */
    public void getEmployeePerformance(Long employeeId) {
        try {
            SalesEmployee employee = entityManager.find(SalesEmployee.class, employeeId);
            if (employee == null) {
                System.out.println("✗ Employee not found with ID: " + employeeId);
                return;
            }

            // Get all leads assigned to this employee
            Query query = entityManager.createQuery(
                    "SELECT l FROM Lead l WHERE l.employee.id = :employeeId", Lead.class);
            query.setParameter("employeeId", employeeId);
            List<Lead> leads = query.getResultList();

            // Count converted and unconverted leads
            long convertedLeads = leads.stream().filter(Lead::isConverted).count();
            long unconvertedLeads = leads.size() - convertedLeads;
            double conversionRate = leads.isEmpty() ? 0 : (convertedLeads * 100.0 / leads.size());

            System.out.println("\n" + "=".repeat(60));
            System.out.println("📊 EMPLOYEE PERFORMANCE REPORT");
            System.out.println("=".repeat(60));
            System.out.println("Employee Name    : " + employee.getName());
            System.out.println("Department       : " + employee.getDepartment());
            System.out.println("Total Leads      : " + leads.size());
            System.out.println("Converted Leads  : " + convertedLeads);
            System.out.println("Unconverted Leads: " + unconvertedLeads);
            System.out.println("Conversion Rate  : " + String.format("%.2f", conversionRate) + "%");
            System.out.println("=".repeat(60) + "\n");

        } catch (Exception e) {
            System.out.println("✗ Error retrieving employee performance: " + e.getMessage());
        }
    }

    /**
     * Get all employees performance
     */
    public void getAllEmployeesPerformance() {
        try {
            Query query = entityManager.createQuery("SELECT e FROM SalesEmployee e", SalesEmployee.class);
            List<SalesEmployee> employees = query.getResultList();

            if (employees.isEmpty()) {
                System.out.println("✗ No employees found in the system");
                return;
            }

            System.out.println("\n" + "=".repeat(80));
            System.out.println("📊 ALL EMPLOYEES PERFORMANCE REPORT");
            System.out.println("=".repeat(80));

            for (SalesEmployee employee : employees) {
                Query leadsQuery = entityManager.createQuery(
                        "SELECT l FROM Lead l WHERE l.employee.id = :employeeId", Lead.class);
                leadsQuery.setParameter("employeeId", employee.getId());
                List<Lead> leads = leadsQuery.getResultList();

                long convertedLeads = leads.stream().filter(Lead::isConverted).count();
                double conversionRate = leads.isEmpty() ? 0 : (convertedLeads * 100.0 / leads.size());

                System.out.printf("%-20s | Dept: %-15s | Leads: %-3d | Converted: %-3d | Rate: %6.2f%%\n",
                        employee.getName(),
                        employee.getDepartment(),
                        leads.size(),
                        convertedLeads,
                        conversionRate);
            }
            System.out.println("=".repeat(80) + "\n");

        } catch (Exception e) {
            System.out.println("✗ Error retrieving all employees performance: " + e.getMessage());
        }
    }

    /**
     * Get top performers
     */
    public void getTopPerformers() {
        try {
            Query query = entityManager.createQuery("SELECT e FROM SalesEmployee e", SalesEmployee.class);
            List<SalesEmployee> employees = query.getResultList();

            if (employees.isEmpty()) {
                System.out.println("✗ No employees found in the system");
                return;
            }

            System.out.println("\n" + "=".repeat(60));
            System.out.println("🏆 TOP PERFORMERS");
            System.out.println("=".repeat(60));

            employees.stream()
                    .map(e -> {
                        Query leadsQuery = entityManager.createQuery(
                                "SELECT l FROM Lead l WHERE l.employee.id = :employeeId", Lead.class);
                        leadsQuery.setParameter("employeeId", e.getId());
                        List<Lead> leads = leadsQuery.getResultList();
                        long convertedLeads = leads.stream().filter(Lead::isConverted).count();
                        double rate = leads.isEmpty() ? 0 : (convertedLeads * 100.0 / leads.size());
                        return new Object[]{e.getName(), leads.size(), convertedLeads, rate};
                    })
                    .sorted((a, b) -> Double.compare((double) b[3], (double) a[3]))
                    .limit(5)
                    .forEach(emp -> System.out.printf("%-20s | Leads: %-3d | Converted: %-3d | Rate: %6.2f%%\n",
                            emp[0], emp[1], emp[2], emp[3]));

            System.out.println("=".repeat(60) + "\n");

        } catch (Exception e) {
            System.out.println("✗ Error retrieving top performers: " + e.getMessage());
        }
    }
}


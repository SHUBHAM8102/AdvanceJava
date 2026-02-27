package pom.capgemini;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class mainEmployee {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");

        Employee employee = (Employee) context.getBean("employee");
        System.out.println(employee);
        employee.msg();

        System.out.println(employee.getId());
        System.out.println(employee.getName());
        System.out.println(employee.getSalary());









    }
}

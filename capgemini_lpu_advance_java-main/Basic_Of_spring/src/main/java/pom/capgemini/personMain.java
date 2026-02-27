package pom.capgemini;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class personMain {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");


        Person person = (Person) context.getBean("person");

        System.out.println(person);

        person.message();

    }
}
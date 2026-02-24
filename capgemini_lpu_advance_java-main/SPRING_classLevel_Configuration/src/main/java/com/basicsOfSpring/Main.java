package com.basicsOfSpring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
	ApplicationContext ioc = new AnnotationConfigApplicationContext(DemoConfiguration.class);
//	Employee e=ioc.getBean(Employee.class);
//	System.out.println(e);
//	System.out.println(e.getId());
//	System.out.println(e.getName());
//	System.out.println(e.getSalary());
	
	Person person=ioc.getBean(Person.class);
	System.out.println(person);
	System.out.println(person.getMobile());
	
	System.out.println(ioc.getBean(Mobile.class));
	
	System.out.println(person.getScan());
	
	}
	
}

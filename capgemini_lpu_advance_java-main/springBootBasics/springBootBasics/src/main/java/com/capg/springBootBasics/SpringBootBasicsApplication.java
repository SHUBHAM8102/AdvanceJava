package com.capg.springBootBasics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringBootBasicsApplication {

	public static void main(String[] args) {
		ApplicationContext ioc=SpringApplication.run(SpringBootBasicsApplication.class, args);
		Doctor doc=ioc.getBean(Doctor.class);
		doc.check();
	}

}

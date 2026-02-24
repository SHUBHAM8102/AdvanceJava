package com.basicsOfSpring;

import java.util.Scanner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages="com")
public class DemoConfiguration {
	
	//this done create a bean for 3rd party library class
	//factory method
	@Bean
	public Scanner getScanner() {
		return new Scanner(System.in);
		
	}

	
	


	
	
	
	
}

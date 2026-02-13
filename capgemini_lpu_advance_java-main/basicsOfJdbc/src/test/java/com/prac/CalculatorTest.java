package com.prac;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CalculatorTest {
	public void addtest() {
	  int res=Calculator.add(20,30);
	}
	@Test
	public void reverseTest() {
	 String res=Calculator.reverseString("data");
	 
	 assertEquals("atad",res);
	 
	}
//	@Test
//	public void reverseTestNull() {
//		String actualres=Calculator.reverseString(null);
//		
//		assertEquals("llun",actualres);
//	}
	@Test
	public void checkFactorial() {
	  int actres=Calculator.factorial(0);
	  assertEquals(1,actres);
	}
	@Test
	public void checkPalindrome() {
		int actre=Calculator.palindrome(121);
		
		assertEquals(121,actre);
	}
	@Test
	public void testValidAge() {
		Employee e=new Employee(10,"Allen",20,"cse");
		assertTrue(e.isValidAge());
	}
	@Test
	public void testValidDept() {
		Employee e=new Employee(10,"Allen",20,"cse123");
		assertFalse(e.isValidDept());
	}
	@Test
	public void testAExc() {
		Calculator c=new Calculator();
		assertThrows(ArithmeticException.class,()->{c.div(10,0);});
	}

}

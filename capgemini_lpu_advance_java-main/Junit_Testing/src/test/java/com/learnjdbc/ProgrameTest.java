package com.learnjdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.connectdatabase.EvenOrodd;
import com.connectdatabase.Program;

public class ProgrameTest {
	@ParameterizedTest
	@ValueSource(strings= {"tenet","radar","aba"})//in place od strings we can write ints,booleans,longs,doubles
	public void isPalindromeTest(String str) {
		Program p=new Program();
		assertTrue(p.isPalindrome(str));
	}
	
	
	@ParameterizedTest
	@CsvSource({
		"1,2,3",
		"5,5,10",
		"5,3,8"
	})
	public void addTest(int a,int b,int excpectedResult) {
		Program p=new Program();
		int actualres=p.add(a, b);
		assertEquals(excpectedResult,actualres);
	}
	
	@ParameterizedTest
	@CsvFileSource(resources="/capgemini.csv",numLinesToSkip=1)
	public void evenorOddTest(String input,String expected) {
		EvenOrodd eo=new EvenOrodd();
		String actualres=eo.evenOrodd(Integer.parseInt(input));
		assertEquals(expected,actualres);
	}

}

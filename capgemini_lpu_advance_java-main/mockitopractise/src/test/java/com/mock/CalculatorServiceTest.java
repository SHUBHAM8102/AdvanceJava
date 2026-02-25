package com.mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

public class CalculatorServiceTest {
	@Test
	public void testDoubleAddition() {
		
		//Step1: create fake object
		Calculator calmock = mock(Calculator.class);
		
		//Step2: what mock object should return
		when(calmock.add(5, 5)).thenReturn(20);
		
		//Step3: inject the mock reference
		CalculatorService service = new CalculatorService(calmock);
		int res = service.doubleAddition(5, 5);
		assertEquals(20, res);
		
		
	}
}

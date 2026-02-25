package com.mock;

public class CalculatorService {
	
	Calculator cal;
	
	CalculatorService(Calculator cal){
		this.cal = cal;
	}
	
	public int doubleAddition(int a, int b) {
		return cal.add(a, b);
//		return -1;
	}
	
}

package com.capg.springBootBasics;

public class Engine {
	public String fuel_type;
	public double cc;
	public Engine(String fuel_type, double cc) {
		
		this.fuel_type = fuel_type;
		this.cc = cc;
	}
	@Override
	public String toString() {
		return "Engine [fuel_type=" + fuel_type + ", cc=" + cc + "]";
	}

}

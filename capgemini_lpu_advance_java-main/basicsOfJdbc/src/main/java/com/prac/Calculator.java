package com.prac;

public class Calculator {
	public static int add(int a,int b) {
		return a+b;
	}
	public static String reverseString(String data) {
		String rev="";
		for(int i=data.length()-1;i>=0;i--) {
			rev=rev+data.charAt(i);
		}
		return rev;
	}
	public static int factorial(int a) {
		int res=1;
		for(int i=1;i<=a;i++) {
			res=res*i;
		}
		return res;
	}
	public static int palindrome(int a) {
		int rev=0;
		while(a>0) {
			int d=a%10;
			rev=rev*10+d;
			a=a/10;
			
		}
		return rev;
	}
	public static int div(int a,int b) {
		return a/b;
	}

}

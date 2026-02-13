package com.connectdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ToReaddata {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		String url = "jdbc:postgresql://localhost:5432/school";
		String username = "postgres";
		String pwd = "root";
		try {
			Class.forName("org.postgresql.Driver");//it is not mandatory
			Connection connect=DriverManager.getConnection(url,username,pwd);
			
			String sql="insert into student values(?,?,?,?)";
			PreparedStatement pstm=connect.prepareStatement(sql);
			System.out.println("Enter id: ");
			int id=sc.nextInt();
			pstm.setInt(1, id);
			
			System.out.println("Enter name: ");
			sc.nextLine();
			String s=sc.nextLine();
			pstm.setString(2, s);
			
			System.out.println("Enter email: ");
			String e=sc.nextLine();
			pstm.setString(3,e);
			
			System.out.println("Enter gender: ");
			String g=sc.nextLine();
			pstm.setString(4,g);
			
			pstm.execute();
			connect.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

}

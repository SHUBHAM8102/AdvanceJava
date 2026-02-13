package com.connectdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//programe to fetch the data
public class FindStudentData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "jdbc:postgresql://localhost:5432/school";
		String username = "postgres";
		String pwd = "root";
		try {
			Class.forName("org.postgresql.Driver");
			
			Connection connect=DriverManager.getConnection(url,username,pwd);
			
			
			Statement stmt=connect.createStatement();
			String sql="select * from student";
			ResultSet res=stmt.executeQuery(sql);
			while(res.next()) {
				System.out.println(res.getInt(1)+" "+res.getString(2)+" "+res.getString(3)+" "+res.getString(4));
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}

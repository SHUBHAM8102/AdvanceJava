package com.connectdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class StepsTOConnectDataBase {
	public static void main(String[] args) {
		String url = "jdbc:postgresql://localhost:5432/school";
		String username = "postgres";
		String pwd = "root";
		// step:1 load the driver class

		try {
			Class.forName("org.postgresql.Driver");// this is used to load the 3rd party jar file present in maven
													// dependencies

			// step2: to establish connection

			Connection connect=DriverManager.getConnection(url, username, pwd);
			//below statement is used to insert
//			String sql="insert into student values(1,'Don','don@gmail.com','female')";
			
			
			
			//below statement is use to update
//			String sql="update student set email='don1234@gmail.com' where id=1";
			
			//below statement is use to delete
			String sql="delete from student where id=1";
			
			
			
			//step:3 create statement interface
			Statement stmt=connect.createStatement();
			//step 4:execute query
			stmt.execute(sql);
			//close connection
			connect.close();
			System.out.println("data inserted..");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // this is used to load the 3rd party jar file present in maven dependencies
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

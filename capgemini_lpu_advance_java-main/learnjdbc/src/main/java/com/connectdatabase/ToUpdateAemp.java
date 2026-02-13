package com.connectdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class ToUpdateAemp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);

		String url = "jdbc:postgresql://localhost:5432/school";
		String username = "postgres";
		String pwd = "root";

		try {
			Class.forName("org.postgresql.Driver");
			Connection connect = DriverManager.getConnection(url, username, pwd);
			String sql = "update student set email=? where id=?";
			PreparedStatement pstm = connect.prepareStatement(sql);

			System.out.println("enter id to update");
			int id = sc.nextInt();
			pstm.setInt(2, id);

			System.out.println("enter new email ");
			String s = sc.next();
			pstm.setString(1, s);

			pstm.close();
			connect.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

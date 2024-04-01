package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Ex02 {

	public static void main(String[] args) throws SQLException {
		// [Ex02.java <--> localhost oracle 서버에 연결]
		//
		String className = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String password = "tiger";
		Connection conn = null;
				
		// Unhandled exception type ClassNotFoundException				
		try {
			// 1. Class.forName() JDBC 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. Connection 객체 생성 - DriverManager.getConnection
			// Unhandled exception type SQLException
			 conn = DriverManager.getConnection(url, user, password);
			 
			// 3. 필요한 작업 (CRUD)
			 System.out.println( conn.isClosed() ? "DB 닫힘": "DB연결" );
			 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 4. Connection 닫기(.close)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}			
		}		
		 
		System.out.println( conn.isClosed() ? "DB 닫힘": "DB연결" );		 				
		
		System.out.println( " end ");		
		
	
		
	}// main

}// class

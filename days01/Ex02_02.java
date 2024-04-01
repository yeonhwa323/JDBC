package days01;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;
import oracle.net.aso.e;

// 내일 복습문제 - 메모장에 시험봄

public class Ex02_02 {

	public static void main(String[] args) {
		
		String className = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String password = "tiger";
		Connection conn = null;
		 				
		//dataSource 객체
		OracleDataSource ds;
		
		try {
			ds = new OracleDataSource();
			ds.setURL(url);
			conn = ds.getConnection(user, password);
			System.out.println(conn.isClosed() );
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
		System.out.println( " end ");				
	
		
	}// main

}// class

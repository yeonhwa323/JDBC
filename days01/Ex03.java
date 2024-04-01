package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ex03 {

	public static void main(String[] args) {
		String className = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String password = "tiger";
		Connection conn = null;
		Statement stmt = null; //명령을수행하는객체선언
		ResultSet rs = null;
		
		int deptno;
		String dname;
		String loc;
		
		try {
			// 1. Class.forName() JDBC 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. Connection 객체 생성 - DriverManager.getConnection
			conn = DriverManager.getConnection(url, user, password);
			
			 
			// 3. 필요한 작업 (CRUD)
			String sql = "SELECT * FROM dept"; //쿼리반드시실행해보기
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			// .next() -> 다음 레코드 있으면 true값주고 다음레코드로 자동이동 / 없으면 false값준다.			
			while ( rs.next() ) {
				/*
				deptno = rs.getInt(1);	//columnIndex
				dname = rs.getString(2);
				loc = rs.getString(3); 
				 */
				
				deptno = rs.getInt("deptno"); // columnLabel(컬럼명)
				dname = rs.getString("dname");
				loc = rs.getString("loc");
				
				System.out.printf("%d\t%s\t%s\n"
						,deptno, dname, loc);
			}
			 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 4. Connection 닫기(.close)
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}			 				
		
		System.out.println( " end ");
		

	}// main

}// class




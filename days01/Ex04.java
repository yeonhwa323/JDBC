package days01;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import domain.EmpVO;

public class Ex04 {

	public static void main(String[] args) {
		// 문제
		// 부서번호( deptno )를 입력받아서
		// emp 테이블에서 select 하는 코딩
		// EmpVO
		// dispEmp(ArrayList<EmpVO> list)
		
		
			String className = "oracle.jdbc.driver.OracleDriver";
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "scott";
			String password = "tiger";
			Connection conn = null;
			Statement stmt = null; //명령을수행하는객체선언
			ResultSet rs = null;
			
			int deptno;
			int empno;
			String ename; 
			String job; 
			int mgr; 
			Date hiredate;
			Double sal;
			Double comm;
			
			//ArrayList<DeptVO> list = null;
			ArrayList<EmpVO> list = new ArrayList<>();
			
			try {
				// 1. Class.forName() JDBC 드라이버 로딩
				Class.forName(className);
				// 2. Connection 객체 생성 - DriverManager.getConnection()
				conn = DriverManager.getConnection(url, user, password);
				
				 
				// 3. 필요한 작업 (CRUD)
				String sql = "SELECT * FROM emp where deptno = 20"; //쿼리반드시실행해보기
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				
				// .next() -> 다음 레코드 있으면 true값주고 다음레코드로 자동이동 / 없으면 false값준다.			
				while ( rs.next() ) {
					
					deptno = rs.getInt("deptno"); // columnLabel(컬럼명)
					empno = rs.getInt("empno");
					ename = rs.getString("ename");
					job = rs.getString("job");
					mgr = rs.getInt("mgr");
					hiredate = rs.getDate("hiredate");
					sal = rs.getDouble("sal");
					comm = rs.getDouble("comm");
							
					EmpVO vo = new EmpVO(empno, ename, job, mgr, hiredate, sal, comm, deptno);
							
					list.add(vo);
					
				}//while
				 
				disEmp(list);
				
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

		private static void disEmp(ArrayList<EmpVO> list) {
			Iterator<EmpVO> ir = list.iterator();
			while ( ir.hasNext()) {
				EmpVO vo = ir.next();
				System.out.println(vo);
				
			}//while
		
	}//disEmp
		
		

}// class
package days02;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import domain.DeptEmpSalgradeVO;
import oracle.jdbc.driver.OracleDriver;


public class Ex01 {

	public static void main(String[] args) {
//		[문제]
//
//				 - emp,dept,salgrade 테이블을 사용해서
//				   ename으로 검색하여 
//				   ArrayList<> 에 저장하여 
//				   dispEmpList(ArrayList<> list) 메서드를 선언하여 출력하는 코딩을 하세요.
//				 - empno, dname, ename, hiredate, pay, grade 컬럼 출력.   
//				 - 검색결과가 없는 경우 
//				    "사원이 존재하지 않습니다." 라고 출력
		
//com.util.DBConn.java
		
		Scanner scanner = new Scanner(System.in);
		String searchWord = null;
		System.out.print("> 검색할 사원명 입력 ?");
		searchWord = scanner.next();
		
		//oracledriver 검색해서 복붙하기!
		String className = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String password = "tiger";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		DeptEmpSalgradeVO vo = null;
		ArrayList<DeptEmpSalgradeVO> list = null;

		int empno,grade;
		String dname, ename;
		Date hiredate;
		double pay;
		
		
		try {
//			1. class.forname() jdbc 드라이버 로딩
			Class.forName(className);
		
//			2. connection 객체생성 : DriverManager
			conn = DriverManager.getConnection(url, user, password);
			
//			3. 필요한 조작(CRUD)
			String sql = 
					String.format(
					"Select empno, dname, ename, hiredate, sal+NVL(comm,0) pay, grade "
					+ "From dept d RIGHT JOIN emp e ON d.deptno = e.deptno "
					+ "            JOIN salgrade s ON e.sal between s.losal and s.hisal"
					+ " where REGEXP_LIKE(ename, '%s', 'i')", searchWord );
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				list = new ArrayList<DeptEmpSalgradeVO>();
				// empno, dname, ename, hiredate, pay, grade
				do {
					empno = rs.getInt("empno");
					dname = rs.getString("dname");
					ename = rs.getString("ename");
					hiredate = rs.getDate("hiredate");
					pay = rs.getDouble("pay");
					grade = rs.getInt("grade"); 
					vo = new DeptEmpSalgradeVO(empno, dname, ename, hiredate, pay, grade);
					list.add(vo);
				} while (rs.next());
				
			}// if
			
			dispEmpList(list);
			
//			4. close() 닫기
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
				
		System.out.println(" end " );
				       
	}// main

	private static void dispEmpList(ArrayList<DeptEmpSalgradeVO> list) {		
		if (list == null) {
			System.out.println( "사원이 존재하지 않습니다.");
			return;		
		}//if
		Iterator<DeptEmpSalgradeVO> ir = list.iterator();
		while (ir.hasNext()) {
			DeptEmpSalgradeVO vo = ir.next();
			System.err.println(vo.toString());
			
		}//while
		
	}//disp

}// class




package days02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBConn;

import domain.DeptVO;
import domain.EmpVO;
import oracle.sql.DATE;

/**
 * @author 조연화
 * @2024. 3. 18. 오후 4:49:14 
 * @subject	
 * @content	
 */

public class Ex03 {
	
	public static Connection conn;	
	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		// 1. DB 사용할 작업 수행
		conn = DBConn.getConnection();
		
			// 1) 사원추가
		
			System.out.print("> 추가할 사원의 부서번호를 입력하세요 ? ");
			int deptno = scanner.nextInt();
			scanner.nextLine();
			
			System.out.println("> 추가할 사원의 사원번호를 입력하세요 ? ");
			int empno = scanner.nextInt();
			
			System.out.println("> 추가할 사원의 사원이름을 입력하세요 ? ");
			String ename = scanner.next();
			
			System.out.println("> 추가할 사원의 사원직업을 입력하세요 ? ");
			String job = scanner.next();
			
			System.out.println("> 추가할 사원의 사수번호을 입력하세요 ? ");
			int mgr = scanner.nextInt();
			
			System.out.println("> 추가할 사원의 입사날짜를 입력하세요 ? ");
			String hiredate = scanner.next();
			
			System.out.println("> 추가할 사원의 임금급액을 입력하세요 ? ");
			int sal = scanner.nextInt();
			
			System.out.println("> 추가할 사원의 커밋금액을 입력하세요 ? ");
			int comm = scanner.nextInt();
						
			

			String sql = " INSERT INTO emp ( deptno, empno, ename, job, mgr, hiredate, sal, comm  )"
					+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )";

			System.out.println(sql);

			//Statement stmt = null;
			PreparedStatement pstmt = null;

			try {
				//stmt = conn.createStatement();
				pstmt = conn.prepareStatement(sql);
				
				// ?, ?, ?  pstmt의 매개변수 설정 X
				// java.sql.SQLException: 부적합한 열 인덱스
				pstmt.setInt(1, deptno);
				pstmt.setInt(2, empno);
				pstmt.setString(3, ename);
				pstmt.setString(4, job);
				pstmt.setInt(5, mgr);
				pstmt.setString(6, hiredate);
				pstmt.setInt(7, sal);
				pstmt.setInt(8, comm);
				
				//int rowCount = stmt.executeUpdate(sql);
				int rowCount = pstmt.executeUpdate();
						

				if (rowCount == 1) {
					System.out.println("사원 추가 성공!!");
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					//stmt.close();
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		
		
		// 6명 
		// 7명
		// [ 팀 문제 ]
		// emp 사원테이블 -
		
		// 2) 사원수정
				
				
		// 3) 사원삭제
		// 4) 사원검색	
		// 5) 사원조회
		
		/*
		 // 내일 아침 테스트문제
	      [실행결과]
	      1등급   (     700~1200 ) - 2명
	            20   RESEARCH   7369   SMITH   800
	            30   SALES         7900   JAMES   950
	      2등급   (   1201~1400 ) - 2명
	         30   SALES   7654   MARTIN   2650
	         30   SALES   7521   WARD      1750   
	      3등급   (   1401~2000 ) - 2명
	         30   SALES   7499   ALLEN      1900
	         30   SALES   7844   TURNER   1500
	      4등급   (   2001~3000 ) - 4명
	          10   ACCOUNTING   7782   CLARK   2450
	         20   RESEARCH   7902   FORD   3000
	         20   RESEARCH   7566   JONES   2975
	         30   SALES   7698   BLAKE   2850
	      5등급   (   3001~9999 ) - 1명   
	         10   ACCOUNTING   7839   KING   5000
	       */  
		}

	}// main

}// class

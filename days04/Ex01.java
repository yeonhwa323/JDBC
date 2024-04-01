package days04;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.util.DBConn;

import domain.DeptVO;
import domain.EmpVO;

/**
 * @author 조연화
 * @date 2024. 3. 20. 오전 8:40:54 
 * @subject	
 * @content	
 */
public class Ex01 {

	public static void main(String[] args) {
		
		String sql = 
				" SELECT dname, d.deptno, count(*) "
				+ " FROM dept d RIGHT JOIN emp e ON d.deptno = e.deptno "
				+ " GROUP By dname, d.deptno "
				+ " ORDER BY deptno" ;
		String empSql = 
				  " SELECT d.deptno, empno, ename, hiredate  \r\n"
				  + "    , sal+NVL(comm,0) pay\r\n"
				  + " FROM emp e JOIN dept d ON d.deptno = e.deptno\r\n"
				  + " ORDER BY sal+NVL(comm,0) DESC"
				  + " WHERE deptno = ? ";
		
		Connection conn = null;
		PreparedStatement pstmt = null, empPstmt = null;
		ResultSet rs = null,  empRs = null;
		DeptVO vo = null;
		ArrayList<EmpVO> empList = null;
		DeptVO empVO = null;
		
		LinkedHashMap<DeptVO, ArrayList<DeptVO>> map = null;
		
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			/*
			if (rs.next()) {
				do {
				int deptno = rs.getInt("deptno");
				vo = new DeptVO(
						deptno 
						, rs.getString("dname")
						, rs.getString("loc")
						);	
				
				empPstmt = conn.prepareStatement(empSql);
				empPstmt.setInt(1, deptno);
				empRs = empPstmt.executeQuery();
				
				if (empRs.next()) {
					empList = new ArrayList<EmpVO>();
					do {
						empVO = EmpVO 
								.builder()
								.empno( empRs.getInt("empno"))
								.ename( empRs.getString("ename"))
								.hiredate( empRs.getDate("hiredate"))
								.pay( empRs.getInt("pay"))
								.build();
						empList.add(empVO);							
					} while (empRs.next());
				}
				map.put(vo, empList);
				empPstmt.close();
				empRs.close();
				
				} while (rs.next());
				
			}//if
			*/
			
			//dispDeptEmp();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
				  
		
		
		

	}// main

		
	
}// class

//[문제]
//		 
//		/*
//		[실행결과]
//ACCOUNTING(10)-3명            dept테이블의 부서번호,부서명, 갯수
//  empno ename hiredate pay	emp테이블
//  empno ename hiredate pay
//  empno ename hiredate pay
//RESEARCH(20)-3명
//  empno ename hiredate pay
//  empno ename hiredate pay
//  empno ename hiredate pay
//SALES(30)-6명
//  empno ename hiredate pay
//  empno ename hiredate pay
//  empno ename hiredate pay
//  empno ename hiredate pay
//  empno ename hiredate pay
//  empno ename hiredate pay
//OPERATIONS(40)-1명
//  empno ename hiredate pay 
//NULL - 1명
//  empno ename hiredate pay
//       */
//       
//
//
//ACCOUNTING(10)-00명
//	  7782 	      CLARK 	  1981-06-09 	 2450.00
//	  7934 	     MILLER 	  1982-01-23 	 1300.00
//RESEARCH(20)-00명
//	  7369 	      SMITH 	  1980-12-17 	 800.00
//	  7566 	      JONES 	  1981-04-02 	 2975.00
//	  7902 	       FORD 	  1981-12-03 	 3000.00
//SALES(30)-00명
//	  7499 	      ALLEN 	  1981-02-20 	 1900.00
//	  7521 	       WARD 	  1981-02-22 	 1750.00
//	  7654 	     MARTIN 	  1981-09-28 	 2650.00
//	  7698 	      BLAKE 	  1981-05-01 	 2850.00
//	  7844 	     TURNER 	  1981-09-08 	 1500.00
//	  7900 	      JAMES 	  1981-12-03 	 950.00
//OPERATIONS(40)-00명
//	  9999 	         동영 	  2017-01-01 	 1100.00
//null(0)-00명
//	  9999 	         동영 	  2017-01-01 	 1100.00    
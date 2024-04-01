package days04;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

import domain.DeptVO;
import domain.EmpVO;

/**
 * @author 조연화
 * @date 2024. 3. 20. 오전 9:43:16 
 * @subject	복습문제 3번풀이
 * @content	
 */
public class Ex01_02 {

	public static void main(String[] args) {
		// ACCOUNTING(10)-3명
		String deptSql = 
				" SELECT d.deptno, dname"
				+ "    , count(*) cnt"
				+ " FROM dept d RIGHT JOIN emp e ON d.deptno = e.deptno"
				+ " GROUP By dname, d.deptno"
				+ " ORDER BY d.deptno ASC ";
		
		String empSql = 
				" SELECT empno, ename, hiredate  "
				+ "    , sal+NVL(comm,0) pay "
				+ " FROM emp "
				+ " WHERE deptno = ? " ;
		// WHERE deptno IS NULL;
		
		Connection conn = null;
		PreparedStatement deptPstmt = null, empPstmt = null ;
		ResultSet deptRs = null, empRs = null ;		
		DeptVO dvo = null;
		EmpVO evo = null;
		
		conn = DBConn.getConnection();
		try {
			deptPstmt = conn.prepareStatement(deptSql);
			deptRs = deptPstmt.executeQuery();
			
			if (deptRs.next()) {
				do {
					int deptno = deptRs.getInt("deptno");
					// 필요한부분만 가지고 생성하기위해 builder() 사용
					dvo = DeptVO.builder()
							.deptno(deptno)
							.dname(deptRs.getString("dname"))
							.cnt(deptRs.getInt("cnt"))
							.build();
					System.out.printf("%s(%d)-%d명\n"
							, dvo.getDname(), dvo.getDeptno() 
							, dvo.getCnt() );
					// START
					empPstmt = conn.prepareStatement(empSql);
					empPstmt.setInt(1, deptno);
					empRs = empPstmt.executeQuery();
					
					if (empRs.next()) {
						do {
							// empno ename hiredate pay
							evo = EmpVO.builder()
									.empno(empRs.getInt(1))
									.ename(empRs.getString(2))
									.hiredate((java.util.Date)empRs.getDate(3))
									.sal(empRs.getDouble(4))
									.build();
							System.out.printf("\t%d \t%s \t%s \t%.2f\n"
									, evo.getEmpno()
									, evo.getEname()
									, evo.getHiredate()
									, evo.getSal());
						} while (empRs.next());
					}// if
					
					empRs.close();
					empPstmt.close();
					// END
					
				} while (deptRs.next());
				
			}//if
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				deptRs.close();
				deptPstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}// main

}// class

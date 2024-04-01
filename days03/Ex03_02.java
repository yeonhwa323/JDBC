package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import com.util.DBConn;

import domain.DeptEmpSalgradeVO;
import domain.SalgradeVO;

/**
 * @author 조연화
 * @date 2024. 3. 19. 오후 4:30:34 
 * @subject	
 * @content	
 */
public class Ex03_02 {

	public static void main(String[] args) {
		
		String sql = 
				  " SELECT grade, losal, hisal "
				+ "    , COUNT(*) cnt "
				+ " FROM salgrade s JOIN emp e ON e.sal BETWEEN s.losal AND s.hisal "
				+ " GROUP BY grade, losal, hisal "
				+ " ORDER BY grade ASC " ;
		String empSql = 
				  " SELECT d.deptno, dname, empno, ename, sal, grade "
				+ " FROM dept d RIGHT JOIN emp e ON d.deptno = e.deptno "
				+ "            JOIN salgrade s ON sal BETWEEN losal AND hisal "
				+ " WHERE grade = ? ";
		
		Connection conn = null;
		PreparedStatement pstmt = null, empPstmt = null ;
		ResultSet rs = null, empRs = null ;
		SalgradeVO vo = null;							// key
		ArrayList<DeptEmpSalgradeVO> empList = null;	// value
		DeptEmpSalgradeVO empVO = null;
		
		LinkedHashMap<SalgradeVO, ArrayList<DeptEmpSalgradeVO>> map = null;
				
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();	
			if (rs.next()) {
				do {
					int grade = rs.getInt("grade");
					// key 값으로 사용될 vo
					vo = new SalgradeVO(
							grade
							, rs.getInt("losal")
							, rs.getInt("hisal")
							, rs.getInt("cnt")
							);
					//
					empPstmt = conn.prepareStatement(empSql);
					empPstmt.setInt(1, grade);
					empRs = empPstmt.executeQuery();
					
					if (empRs.next()) {
						empList = new ArrayList<DeptEmpSalgradeVO>();
						do {
							// d.deptno, dname, empno, ename, sal, grade
							empVO = DeptEmpSalgradeVO
									.builder()
									.empno(empRs.getInt("empno"))
									.dname(empRs.getString("dname"))
									.ename(empRs.getString("ename"))
									.pay(empRs.getDouble("sal"))
									.build();
							empList.add(empVO);	
						} while (empRs.next());
					}
					
					map.put(vo, empList);
					
					empRs.close();
					empPstmt.close();
					
				} while (rs.next());					
			}// if
					
			dispSalgrade(map);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				DBConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}// main

	private static void dispSalgrade(
			LinkedHashMap<SalgradeVO, ArrayList<DeptEmpSalgradeVO>> map) {
		
		
	}

	private static void dispSalgrade(ArrayList<SalgradeVO> list) {
		Iterator<SalgradeVO> ir = list.iterator();
		while (ir.hasNext()) {
			SalgradeVO vo = ir.next();
			System.out.printf("%d등급 ( %d ~ %d ) - %d 명\n"
					, vo.getGrade()
					, vo.getLosal()
					, vo.getHisal()
					, vo.getCnt());			
			
		}// while
		
	}

}// class


/*
[실행결과]
1등급   (     700~1200 ) - 2명						key
      20   RESEARCH   7369   SMITH   800			value
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

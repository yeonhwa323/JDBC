package days04;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.util.DBConn;

import domain.DeptVO;
import domain.EmpVO;

/**
 * @author 조연화
 * @date 2024. 3. 20. 오전 9:43:16 
 * @subject	Map
 * @content	문제점) 부서가 존재하지 않는 KING 사원처리x
 * 					WHERE deptno IS NULL 실행
 * 					관련 코딩을 수정해야 한다.
 */
public class Ex01_03 {

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
		
		ArrayList<EmpVO> list = null;
		LinkedHashMap<DeptVO, ArrayList<EmpVO>> map = new LinkedHashMap<DeptVO, ArrayList<EmpVO>>();
		
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
//					System.out.printf("%s(%d)-%d명\n"
//							, dvo.getDname(), dvo.getDeptno() 
//							, dvo.getCnt() );
					// START
					empPstmt = conn.prepareStatement(empSql);
					empPstmt.setInt(1, deptno);
					empRs = empPstmt.executeQuery();
					
					if (empRs.next()) {
						list = new ArrayList<EmpVO>();
						do {
							// empno ename hiredate pay
							evo = EmpVO.builder()
									.empno(empRs.getInt(1))
									.ename(empRs.getString(2))
									.hiredate((java.util.Date)empRs.getDate(3))
									.sal(empRs.getDouble(4))
									.build();
							list.add(evo);
//							System.out.printf("\t%d \t%s \t%s \t%.2f\n"
//									, evo.getEmpno()
//									, evo.getEname()
//									, evo.getHiredate()
//									, evo.getSal());
						} while (empRs.next());
					}// if
					
					map.put(dvo, list);
					
					empRs.close();
					empPstmt.close();
					// END
					
				} while (deptRs.next());
				
			}//if
			
			dispEmp(map);
			
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

	private static void dispEmp(LinkedHashMap<DeptVO, ArrayList<EmpVO>> map) {
		Set<Entry<DeptVO, ArrayList<EmpVO>>> set = map.entrySet();
		Iterator<Entry<DeptVO, ArrayList<EmpVO>>> ir = set.iterator();
		
		while (ir.hasNext()) {
			Entry<DeptVO, ArrayList<EmpVO>> entry = ir.next();
			DeptVO dvo = entry.getKey();
			ArrayList<EmpVO> list = entry.getValue();
			
			System.out.printf("%s(%d)-%d명\n"
					, dvo.getDname(), dvo.getDeptno()
					, dvo.getCnt() );
			
			Iterator<EmpVO> ir2 = list.iterator();
			while (ir2.hasNext()) {
				EmpVO evo = (EmpVO) ir2.next();
				System.out.printf("\t%d \t%s \t%s \t%.2f\n",
		                   evo.getEmpno()
		                   , evo.getEname()
		                   , evo.getHiredate()
		                   , evo.getSal());

				
			}//while2
			
		}//while1
		
	}

}// class

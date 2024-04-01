package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import domain.EmpVO;

/**
 * @author 조연화
 * @2024. 3. 19. 오후 2:40:43 
 * @subject	
 * @content	
 */
public class EmpDAOImpl implements EmpDAO {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private int rowCount;

	// 생성자
	public EmpDAOImpl(Connection conn) {
		this.conn = conn;
	}

	// 2. Setter를 통한 DI
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	@Override
	public ArrayList<EmpVO> getEmpSelect() {

		ArrayList<EmpVO> list = null;

		int empno;
		String ename;
		String job;
		int mgr;
		Date hiredate;
		Double sal;
		Double comm;
		int deptno;

		String sql = "SELECT * "
				+ "FROM emp";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				list = new ArrayList<>();
				do {
					empno = rs.getInt(1);
					ename = rs.getString(2);
					job = rs.getString(3);
					mgr = rs.getInt(4);
					hiredate = rs.getDate(5);
					sal = rs.getDouble(6);
					comm = rs.getDouble(7);
					deptno = rs.getInt(8);

					EmpVO vo = new EmpVO(empno, ename, job, mgr, null, sal, comm, deptno);
					list.add(vo);
				} while (rs.next());
			}// if


		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	@Override
	public int addEmp(EmpVO vo) {

		String sql = " INSERT INTO emp ( deptno, empno, ename, job, mgr, hiredate, sal, comm  )"
					+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )" ;

		//System.out.println(sql);

		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, vo.getDeptno() );
			pstmt.setInt(2, vo.getEmpno() );
			pstmt.setString(3, vo.getEname() );
			pstmt.setString(4, vo.getJob() );
			pstmt.setInt(5, vo.getMgr());
			// java.util.Date -> java.sql.Date 변환
	        // pstmt.setDate(6, (java.sql.Date) vo.getHiredate());
			long ms = vo.getHiredate().getTime() ; // java.util.Date
			java.sql.Date sdhiredate = new java.sql.Date(ms);			
			pstmt.setDate(6, sdhiredate );			
			pstmt.setDouble(7, vo.getSal() );
			pstmt.setDouble(8, vo.getComm() );

			rowCount = pstmt.executeUpdate();

	      } catch (SQLException e) {
	         e.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try { 
	            pstmt.close();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         } 
	      }

	      return rowCount;
	   }

	@Override
	public int updateEmp(EmpVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEmp(int empno) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<EmpVO> searchEmp(int searchCondition, String searchWord) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmpVO getEmp(int empno) {
		// TODO Auto-generated method stub
		return null;
	}
}

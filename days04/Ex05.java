package days04;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

import domain.DeptVO;
import oracle.jdbc.OracleTypes;

/**
 * @author 조연화
 * @date 2024. 3. 20. 오후 2:01:14 
 * @subject	dept - select 부서조회
 * @content	 up_selectdept
 */
public class Ex05 {

	public static void main(String[] args) {
		
		String sql = "{CALL UP_SELECTDEPT(?)}";
		
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		conn = DBConn.getConnection();
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);  // OUT
			cstmt.executeQuery();
			
			rs = (ResultSet) cstmt.getObject(1);
			
			// boolean rs.isFirst();
			DeptVO vo = null;
			
			while (rs.next()) {
				int deptno = rs.getInt(1);
				String dname = rs.getString(2);
				String loc = rs.getString(3);
				// vo = new DeptVO(deptno, dname, loc);
				vo = DeptVO.builder()
						.deptno(deptno)
						.dname(dname)
						.loc(loc)
						.build() ;
				System.out.printf(" %d \t %s \t %s \n "
						, deptno, dname, loc );
				
			}//while
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				cstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		DBConn.close();
		System.out.println(" end ");

	}//main

}//class

/*
-- up_selectdept
CREATE OR REPLACE PROCEDURE up_selectdept
(
   pdeptcursor OUT SYS_REFCURSOR
)
IS
BEGIN    
    -- OPEN 커서 FOR 문
    OPEN pdeptcursor FOR  SELECT * FROM dept;
END;
*/

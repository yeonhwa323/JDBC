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
 * @subject	dept - update 부서추가
 * @content	 up_updatedept
 */
public class Ex05_03 {

	public static void main(String[] args) {
		
		//String sql = "{CALL UP_UPDATEDEPT(?, ?, ?)}";
		String sql = "{CALL UP_UPDATEDEPT(?, pdname=> ?)}";
		//String sql = "{CALL UP_INSERTDEPT(ploc=> ?)}";
		
		Connection conn = null;
		CallableStatement cstmt = null;
		int rowCount = 0;
		
		int pdeptno = 50;		
		String pdname = "QC", ploc = "SEOUL2";
		
		conn = DBConn.getConnection();
		try {
			cstmt = conn.prepareCall(sql);
			
			cstmt.setInt(1, pdeptno); // IN
			cstmt.setString(2, pdname);   // IN
			//cstmt.setString(3, ploc);   // IN
			
			rowCount = cstmt.executeUpdate(); // DML 문
			
			if (rowCount == 1 ) {
				System.out.println("부서 수정 성공!!");
			} else {
				System.out.println("부서 수정 실패!!");
			}
			
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
--up_updatedept 부서 수정
CREATE OR REPLACE PROCEDURE up_updatedept
(
   pdeptno  IN dept.deptno%TYPE
   , pdname IN dept.dname%TYPE := NULL
   , ploc   IN dept.loc%TYPE  := NULL
)
IS
BEGIN    
    UPDATE dept
    SET dname = NVL(pdname, dname) , loc = NVL(ploc, loc)
    WHERE deptno = pdeptno;
    COMMIT;
--EXCEPTION
END;
-- Procedure UP_UPDATEDEPT이(가) 컴파일되었습니다.
*/

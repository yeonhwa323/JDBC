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
 * @subject	dept - delete 부서 삭제
 * @content	 up_deletedept
 */
public class Ex05_04 {

	public static void main(String[] args) {
		
		String sql = "{CALL UP_DELETEDEPT(?)}";
		
		Connection conn = null;
		CallableStatement cstmt = null;
		int rowCount = 0;
		
		int pdeptno = 50;
		
		conn = DBConn.getConnection();
		try {
			cstmt = conn.prepareCall(sql);
			
			cstmt.setInt(1, pdeptno); // IN
			
			rowCount = cstmt.executeUpdate(); // DML 문
			
			if (rowCount == 1 ) {
				System.out.println("부서 삭제 성공!!");
			} else {
				System.out.println("부서 삭제 실패!!");
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
--up_deletedept 부서 삭제
CREATE OR REPLACE PROCEDURE up_deletedept
(
   pdeptno  IN dept.deptno%TYPE
)
IS
BEGIN    
    DELETE FROM dept
    WHERE deptno = pdeptno;
    COMMIT;
--EXCEPTION
END;
-- Procedure UP_DELETEDEPT이(가) 컴파일되었습니다.
*/

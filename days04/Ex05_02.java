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
 * @subject	dept - insert 부서추가
 * @content	 up_insertdept
 */
public class Ex05_02 {

	public static void main(String[] args) {
		
		String sql = "{CALL UP_INSERTDEPT(?, ?)}";
		//String sql = "{CALL UP_INSERTDEPT(pdname=> ?)}";
		//String sql = "{CALL UP_INSERTDEPT(ploc=> ?)}";
		
		Connection conn = null;
		CallableStatement cstmt = null;
		int rowCount = 0;
		
		String pdname = "QC", ploc = "SEOUL";
		
		conn = DBConn.getConnection();
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, pdname); // IN
			cstmt.setString(2, ploc);   // IN
			rowCount = cstmt.executeUpdate(); // DML 문
			
			if (rowCount == 1 ) {
				System.out.println("부서 추가 성공!!");
			} else {
				System.out.println("부서 추가 실패!!");
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
-- 시퀀스 확인
select *
from user_sequences;
-- 시퀀스 생성 seq_deptno
drop sequence seq_deptno;
--
CREATE sequence seq_deptno
INCREMENT BY 10
START WITH 50
NOCACHE;
--
CREATE OR REPLACE PROCEDURE up_insertdept
(
   pdname dept.dname%TYPE
   , ploc dept.loc%TYPE
)
IS
BEGIN    
    INSERT INTO dept ( deptno, dname, loc )
    VALUES ( seq_deptno.nextval, pdname, ploc );
    COMMIT;
END;
-- Procedure UP_INSERTDEPT이(가) 컴파일되었습니다.
*/

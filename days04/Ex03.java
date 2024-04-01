package days04;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.util.DBConn;

import oracle.jdbc.OracleTypes;

/**
 * @author 조연화
 * @date 2024. 3. 20. 오전 11:23:01 
 * @subject	
 * @content	
 */
public class Ex03 {

	public static void main(String[] args) {
	// https://docs.oracle.com/cd/E17781_01/appdev.112/e18805/addfunc.htm#TDPJD209
	// CallableStatement - 저장 프로시저, 저장함수
	// [저장 프로시저] : 입력받은 ID를 사용 여부 체크하는 프로시저
	//	    ㄴ 회원가입
		//         아이디 : [  hong  ] < ID중복체크버튼 >
		//         비밀번호
		//         이메일
		//         주소
		//         연락처
		//         등등
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("> 중복 체크할 ID(empno)를 입력 ? ");
		int id = scanner.nextInt(); // 7369, 8888
		
		Connection conn = null;
		CallableStatement cstmt = null;
		int check = 0;
		
		String sql = "{call up_idcheck(?,?)}" ;
		//String sql = "{call up_idcheck(pid=>?,pcheck=>?)}" ;
		
		conn = DBConn.getConnection();
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, id); // IN
			 // OUT 설정 X, cstmt 출력용 매개변수
			cstmt.registerOutParameter(2, OracleTypes.INTEGER);
			cstmt.executeQuery();
			
			check = cstmt.getInt(2);
			
			if (check == 0) {
				System.out.println("사용 가능한 ID(empno)입니다. ");
			} else {
				System.out.println("이미 사용중인 ID(empno)입니다. ");
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
		
	}// main

}// class

/*
-- ID 중복체크하는 저장 프로시저
-- [emp 테이블의 empno(사원번호) 를 ID로 생각하고 구현]
CREATE OR REPLACE PROCEDURE up_idcheck
(
    pid IN emp.empno%TYPE
    , pcheck OUT NUMBER
)
IS
BEGIN
    SELECT COUNT(*) INTO pcheck -- 존재하는 사원번호(ID) 1 -> 사용불가, 존재X (0) 사용가능
    FROM emp
    WHERE empno = pid;
--EXCEPTION
--    WHEN OTHERS THEN
END;

-- (꼭 기억) 저장 프로시저를 테스트를 한 후에 자바코딩에서 사용하자.
DECLARE 
    VCHECK NUMBER(1);
BEGIN
    UP_IDCHECK( 7369, vcheck );    
    DBMS_OUTPUT.PUT_LINE(vcheck);    
END;
*/

/*
//JDBC syntaxCallableStatement cs1 = conn.prepareCall
( "{call proc (?,?)}" ) ; // stored proc
CallableStatement cs2 = conn.prepareCall
( "{? = call func (?,?)}" ) ; // stored func

//Oracle PL/SQL block syntax
CallableStatement cs3 = conn.prepareCall
( "begin proc (?,?); end;" ) ; // stored proc
CallableStatement cs4 = conn.prepareCall
( "begin ? := func(?,?); end;" ) ; // stored func
*/


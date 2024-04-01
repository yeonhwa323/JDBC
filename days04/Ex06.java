package days04;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.util.DBConn;

import domain.DeptVO;
import oracle.jdbc.OracleTypes;

/**
 * @author 조연화
 * @date 2024. 3. 20. 오후 2:50:35 
 * @subject	[자바 리플렉션(reflection)] - 검색
 * @content	- 반사, 상, 반영 의미
 * 			- JDBC 리플렉션 ? 결과물(rs)에 대한
 * 			  정보(?)를 추출해서 사용할 수 있는 기술. 동적으로 처리하는 기술.
 */
// https://blog.naver.com/hj_kim97/223110095000
public class Ex06 {

	public static void main(String[] args) {
		
		String sql = "SELECT table_name FROM tabs ";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<String> tnList = new ArrayList<>();
		String tableName = null;
		
		conn = DBConn.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				tableName = rs.getString(1);
				tnList.add(tableName);
			}// while
			
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
		// [1] tnList 출력
		System.out.println( tnList.toString()  );
		
		// [2]
		Scanner scanner = new Scanner(System.in);
		System.out.printf("> 테이블명을 입력 ? ");
		tableName = scanner.next();
		// (기억) 테이블명, 컬럼명은 pstmt의 매개변수로 사용X
		//sql = "SELECT * FROM ? " ;
		sql = " SELECT * FROM " + tableName ;	
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			// rs
			ResultSetMetaData  rsmd = rs.getMetaData();
			
			//System.out.println("> 컬럼수 : " + rsmd.getColumnCount());
			int columnCount = rsmd.getColumnCount();
			
			System.out.println("-".repeat(columnCount * 7));
			/*
			for (int i = 1; i <= columnCount; i++) {
				String columnName = rsmd.getColumnName(i);
				String columnType = rsmd.getColumnTypeName(i);
				if (columnType.equals("NUMBER")) {
					// NUMBER(p, s)
					// NUMBER(p)
					int precision = rsmd.getPrecision(i);
					int scale = rsmd.getScale(i);
					System.out.printf("%s %s(%d,%d)\n"
							, columnName, columnType
							, precision, scale );
				} else {
					System.out.printf("%s %s \n" , columnName, columnType);		
				}
						
			}// for
			*/
			// 컬럼명 출력
			for (int i = 1; i <= columnCount; i++) {
				String columnName = rsmd.getColumnName(i);
				System.out.printf("%s " , columnName );		
										
			}// for
			System.out.println(); // 개행				
			System.out.println("-".repeat(columnCount * 7));
			// rs 레코드 출력
			// 2  - NUMBER
			// 12 - VARCHAR2
			// 93 - DATE
			
 			while (rs.next()) {
 				for (int i = 1; i <= columnCount; i++) {
					int columnType = rsmd.getColumnType(i);
					if (columnType == 2) {
						// scale = 0 "정수"의미
						int scale = rsmd.getScale(i);
						if (scale == 0) { // 정수
							System.out.printf("%d \t ", rs.getInt(i));						
						} else { // 실수
							System.out.printf("%.2f \t ", rs.getDouble(i));
													}
					}
					else if (columnType==12) { // VARCHAR2
						System.out.printf("%s \t", rs.getString(i));
					} else if (columnType==93) { // DATE
						System.out.printf("%tF \t", rs.getDate(i));
					}
				}//for
				
			}//while
			
			System.out.println("-".repeat(columnCount * 7));			
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
				
		
		// [3]
		DBConn.close();
		System.out.println(" end ");		

	}//main

}//class

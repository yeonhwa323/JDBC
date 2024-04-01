
package days03;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBConn;

import domain.DeptVO;

/**
 * @author 연화
 * @date 2024. 3. 19. - 오전 7:49:26
 * @subject    PreparedStatement 사용해서 코딩 수정.
 * @content
 */
public class Ex01 {

	public static String [] menu = { "추가", "수정", "삭제", "조회", "종료", "검색" };
	public static int selectedNumber ;
	public static Connection conn;
	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) { 
		
		conn = DBConn.getConnection();

		do {
			메뉴출력();
			메뉴선택();
			메뉴처리();         
		} while (true);

	} // main

	private static void 메뉴처리() { 

		switch (selectedNumber) {
		case 1:  // 추가
			부서추가();
			break;
		case 2:  // 수정
			부서수정();
			break;
		case 3:  // 삭제
			부서삭제();
			break;
		case 4: // 조회
			부서조회();
			break;
		case 5: // 종료
			프로그램종료();
			break;
		case 6: // 검색
			부서검색();
			break;
		default:
			break;
		} // switch

		일시정지();
	}

	private static void 부서검색() {
	      // 검색조건 입력 ?  1(부서번호)   2(부서명)  3(지역명)
	      // SELECT * FROM dept
		  // WHERE deptno = 10;   -- 1
		  // WHERE REGEXP_LIKE( dname, 'SL', 'i');  -- 2
	      // WHERE REGEXP_LIKE( loc, 'lo', 'i');  -- 3
	      	      
	      // 검색어   입력 ? 

	      int searchCondition = 1;   // 1,2,3 검색조건, 4(dname, loc)
	      String searchWord = null;  // 검색어

	      //                      1       20
	      System.out.print("> 검색조건, 검색어 입력하세요 ? ");
	      searchCondition = scanner.nextInt();
	      searchWord = scanner.nextLine().trim();

	      // 부서조회() + 복붙
	      ArrayList<DeptVO> list = null;
	      ResultSet rs = null;
	      //Statement stmt = null;
	      PreparedStatement pstmt = null;
	      
	      String sql = " SELECT * "
		            + " FROM dept "
		            + " WHERE ";

	      if (searchCondition == 1) {
	         sql += " deptno = ? ";
	      } else if (searchCondition == 4) {
	         sql += 
	               " REGEXP_LIKE( dname , ?, 'i' ) OR  REGEXP_LIKE( loc , ?, 'i' )" ;
	      } else { // 2,3
	    	  sql += String.format(
	 	               " REGEXP_LIKE( %s, ?, 'i' ) "
	 	        		 , searchCondition==2? "dname":"loc" );	                    
	      }

	      System.out.println(  sql );


	      int deptno;
	      String dname, loc;
	      DeptVO vo = null;

	      try {
	         //stmt = conn.createStatement();
	    	  pstmt = conn.prepareStatement(sql);
	         //rs = stmt.executeQuery(sql);
	    	  if (searchCondition == 1) {
	    		  pstmt.setInt(1, Integer.parseInt(searchWord));
	 	         
	 	      } else if (searchCondition == 4) {
	 	         pstmt.setString(1, searchWord); // searchWord : 테이블이나 컬럼명을 ?로 사용불가
	 	         pstmt.setString(2, searchWord);
	 	         
	 	      } else { // 2,3
	 	    	 pstmt.setString(1, searchWord);         
	 	      }	    	  
	    	  rs = pstmt.executeQuery();
	    	  
	         if (rs.next()) {
	            list = new ArrayList<DeptVO>();
	            do {
	               deptno = rs.getInt("deptno");
	               dname = rs.getString("dname");
	               loc = rs.getString("loc");
	               vo = new DeptVO(deptno, dname, loc);
	               list.add(vo);
	            } while (rs.next());
	            부서출력(list);
	         } // 
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } catch (Exception e) {
		         e.printStackTrace();
		  }finally {
	         try {
	           // stmt.close();
	           pstmt.close();
	           rs.close();			// NullPointerException = rs 가 현재null값임을 의미
	         } catch (SQLException e) { 
	            e.printStackTrace();
	         }
	      }
	   }


	// [2]
	private static void 부서삭제() {
		//java.sql.SQLSyntaxErrorException: ORA-01722: invalid number
		
		String deptno ;
		System.out.print("> 삭제할 부서번호를 입력 ? ");
		deptno = scanner.nextLine(); // _50,_60,__70
		
		String [] deptnoArr = deptno.trim().split("\s*,\s*");

		String sql =  " DELETE FROM dept "
					+ " WHERE deptno IN ( " ;		
		// ?, ?, ?
		for (int i = 0; i < deptnoArr.length; i++) {
			sql += "?, ";
		}
		sql = sql.substring(0, sql.length()-2);
		sql +=" )";
		
		System.out.println( sql );
		
		
		//Statement stmt = null;
		PreparedStatement pstmt = null;
		
		try { 
			//stmt = conn.createStatement(); 
			pstmt = conn.prepareStatement(sql);
			
			//int rowCount = stmt.executeUpdate(sql);
			
			for (int i = 0; i < deptnoArr.length; i++) {
				pstmt.setString(1+i, deptnoArr[i]);
			}
			int rowCount = pstmt.executeUpdate();
			
			if( rowCount >= 1 ) {
				System.out.println(" 부서 삭제 성공!!!");
			} 

		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			try {
				//stmt.close();
				pstmt.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}

	}

	/* [1]
		   private static void 부서삭제() {
		      String deptno ;
		      System.out.print("> 삭제할 부서번호를 입력 ? ");
		      deptno = scanner.next();

		      String sql =  String.format(
		                   " DELETE FROM dept "
		                 + " WHERE deptno = %s", deptno);

		      System.out.println( sql );

		      Statement stmt = null;

		      try { 
		         stmt = conn.createStatement(); 
		         int rowCount = stmt.executeUpdate(sql);

		         if( rowCount == 1 ) {
		            System.out.println(" 부서 삭제 성공!!!");
		         } 

		      } catch (SQLException e) { 
		         e.printStackTrace();
		      } finally {
		         try {
		            stmt.close();
		         } catch (SQLException e) { 
		            e.printStackTrace();
		         }
		      }

		   }
	 */

	// [3] 원대안          pdeptno=50,pdname=>XX,ploc=>YY
	// [3] 원대안          pdeptno=50,ploc=>YY
	// [3] 원대안          pdeptno=50,pdname=>XX
	/*
		   private static void 부서수정() throws IOException{
		         System.out.println("> 수정할 부서번호, 부서명, 지역명 입력하세요");
		         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		         System.out.println("부서 번호 입력하세요 ");
		         int deptno = Integer.parseInt(br.readLine());

		         System.out.println("부서명 입력하세요 ");
		         String dname = "";
		         dname = br.readLine();

		         System.out.println("지역명 입력하세요 ");
		         String loc = "";
		         loc = br.readLine();

		         String sql = "SELECT * FROM dept WHERE deptno = " + deptno;
		         Statement stmt = null;
		         try {
		            ResultSet rs = null;
		            stmt = conn.createStatement();
		            rs = stmt.executeQuery(sql);

		            rs.next();

		            dname = dname.isEmpty() ? rs.getString("dname") : dname;
		            loc = loc.isBlank() ? rs.getString("loc") : loc;

		            sql = "";
		            sql = String.format("UPDATE dept "
		                  + " SET dname = '%s', loc = '%s'"
		                  + " WHERE deptno = %d ", dname,loc,deptno);
		            int rowcount = stmt.executeUpdate(sql);
		            if(rowcount == 1) {
		               System.out.println(rowcount + "수정 성공");
		            } else {
		               System.out.println("실패");
		            }
		            stmt.close();
		            rs.close();

		         } catch (SQLException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		         }
		      }
	 */

	// [3]
	/*
		   private static void 부서수정() {
		      int deptno = 0 ;
		      String dname = null, loc = null;

		      System.out.print("> 수정할 부서번호,부서명,지역명 입력하세요 ? ");
		      String record  = scanner.next();

		      String pattern = "{0},{1},{2}";
		      MessageFormat mf = new MessageFormat(pattern);
		      try {
		         Object [] objs = mf.parse(record);

		         deptno =  Integer.parseInt(objs[0].toString()) ;
		         dname = objs[1].toString() ;
		         loc = objs[2].toString() ;

		      } catch (ParseException e) {
		         e.printStackTrace();
		      }

		      String sql = null ;

		      if ( dname.equals("")) {
		         sql = String.format(
		               " UPDATE  dept "
		                     + " SET loc = '%s' "
		                     + " WHERE deptno = %d ", loc, deptno );
		      } else if (loc.equals("")) {
		         sql = String.format(
		               " UPDATE  dept "
		                     + " SET dname = '%s' "
		                     + " WHERE deptno = %d ", dname, deptno );
		      } else {
		         sql = String.format(
		               " UPDATE  dept "
		                     + " SET dname = '%s',loc = '%s' "
		                     + " WHERE deptno = %d ", dname, loc, deptno );
		      }

		      System.out.println( sql );  // 쿼리 확인

		      Statement stmt = null;

		      try { 
		         stmt = conn.createStatement(); 
		         int rowCount = stmt.executeUpdate(sql);

		         if( rowCount == 1 ) {
		            System.out.println(" 부서 수정 성공!!!");
		         }

		         // COMMIT or ROLLBACK 
		      } catch (SQLException e) { 
		         e.printStackTrace();
		      } finally {
		         try {
		            stmt.close();
		         } catch (SQLException e) { 
		            e.printStackTrace();
		         }
		      }

		   }
	 */

	// 3:06 수업시작
	// [2] 
	private static void 부서수정() {

		System.out.print("> 수정할 부서번호 입력하세요 ? ");
		int deptno = scanner.nextInt(); 
		scanner.nextLine();  // \r\n

		System.out.print("> 수정할 부서명 입력하세요 ? ");
		String dname = scanner.nextLine(); // QC\r\n       ""

		System.out.print("> 수정할 지역명 입력하세요 ? ");
		String loc = scanner.nextLine();

		String sql = null ;

		if ( dname.equals("")) {
			sql =  " UPDATE  dept "
					+ " SET loc = ? "
					+ " WHERE deptno = ? ";
		} else if (loc.equals("")) {
			sql = 
					" UPDATE  dept "
							+ " SET dname = ? "
							+ " WHERE deptno = ? ";
		} else {
			sql = 
					" UPDATE  dept "
							+ " SET dname = ?,loc = ? "
							+ " WHERE deptno = ? ";
		}

		// System.out.println( sql );  // 쿼리 확인

		//Statement stmt = null;
		PreparedStatement pstmt = null;

		try { 
			// stmt = conn.createStatement();
			pstmt = conn.prepareStatement(sql);

			//int rowCount = stmt.executeUpdate(sql);
			// ? 매개변수 설정 X

			if ( dname.equals("")) {
				pstmt.setString(1,loc);
				pstmt.setInt(2, deptno);
				
			} else if (loc.equals("")) {
				pstmt.setString(1,dname);
				pstmt.setInt(2, deptno);				
				
			} else {
				pstmt.setString(1,dname);
				pstmt.setString(2,loc);
				pstmt.setInt(3, deptno);
			}
			
			int rowCount = pstmt.executeUpdate();

			if( rowCount == 1 ) {
				System.out.println(" 부서 수정 성공!!!");
			}

			// COMMIT or ROLLBACK 
		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			try {
				//stmt.close();
				pstmt.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}

	} 

	/* [1]
		   private static void 부서수정() {

		      System.out.print("> 수정할 부서번호,부서명,지역명 입력하세요 ? ");

		      int deptno = scanner.nextInt();
		      String dname = scanner.next();
		      String loc = scanner.next();

		      String sql = String.format(
		              " UPDATE  dept "
		            + " SET dname = '%s',loc = '%s' "
		            + " WHERE deptno = %d ", dname, loc, deptno );

		      System.out.println( sql );  // 쿼리 확인

		      Statement stmt = null;

		      try { 
		         stmt = conn.createStatement(); 
		         int rowCount = stmt.executeUpdate(sql);

		         if( rowCount == 1 ) {
		            System.out.println(" 부서 수정 성공!!!");
		         }

		         // COMMIT or ROLLBACK 
		      } catch (SQLException e) { 
		         e.printStackTrace();
		      } finally {
		         try {
		            stmt.close();
		         } catch (SQLException e) { 
		            e.printStackTrace();
		         }
		      }

		   }
	 */

	private static void 부서추가() {

		System.out.print("> 부서번호,부서명,지역명 입력하세요 ? ");

		int deptno = scanner.nextInt();
		String dname = scanner.next();
		String loc = scanner.next();

		String sql = "INSERT INTO dept (deptno,dname,loc ) "
				+ " VALUES ( ?, ?, ? )";

		// System.out.println( sql); 쿼리 확인

		//Statement stmt = null;
		PreparedStatement pstmt = null;

		try {         
			//stmt = conn.createStatement();
			pstmt = conn.prepareStatement(sql);         

			//int rowCount = stmt.executeUpdate(sql);
			// java.sql.SQLException: 인덱스에서 누락된 IN 또는 OUT 매개변수:: 1

			// ?, ?, ?  pstmt의 매개변수 설정 X
			pstmt.setInt(1, deptno);
			pstmt.setString(2, dname);
			pstmt.setString(3, loc);

			int rowCount = pstmt.executeUpdate();

			if( rowCount == 1 ) {
				System.out.println(" 부서 추가 성공!!!");
			}

			// COMMIT or ROLLBACK 
		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			try {
				// stmt.close();
				pstmt.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}

	}

	private static void 일시정지() {
		System.out.print("엔터치면 계속합니다.");
		try {
			System.in.read();
			System.in.skip( System.in.available() );
		} catch (IOException e) { 
			e.printStackTrace();
		}      
	}

	private static void 부서조회() {
		ArrayList<DeptVO> list = null;
		ResultSet rs = null;
		// Statement stmt = null;
		PreparedStatement pstmt = null;

		String sql = "SELECT * "
				+ "FROM dept";

		int deptno;
		String dname, loc;

		DeptVO vo = null;

		try {
			// stmt = conn.createStatement();
			pstmt = conn.prepareStatement(sql);

			// rs = stmt.executeQuery(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				list = new ArrayList<DeptVO>();
				do {
					deptno = rs.getInt("deptno");
					dname = rs.getString("dname");
					loc = rs.getString("loc");
					vo = new DeptVO(deptno, dname, loc);
					list.add(vo);
				} while (rs.next());
				부서출력(list);
			} // 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// stmt.close();
				pstmt.close();
				rs.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}


	}

	private static void 부서출력(ArrayList<DeptVO> list) {
		Iterator<DeptVO> ir = list.iterator();
		System.out.println("-".repeat(30));
		System.out.printf("deptno\tdname\tloc\n");
		System.out.println("-".repeat(30));
		while (ir.hasNext()) {
			DeptVO vo =  ir.next();
			System.out.printf("%d\t%s\t%s\n"
					, vo.getDeptno(), vo.getDname(), vo.getLoc());
		}
		System.out.println("-".repeat(30));
	}

	private static void 프로그램종료() {
		// 1. DB 닫기
		DBConn.close();
		// 1-2. 스캐너 닫기
		scanner.close();
		// 2. 종료 메시지 출력
		System.out.println("프로그램 종료!!!");
		// 3. 
		System.exit(-1);
	}

	private static void 메뉴선택() {
		// Scanner scanner = new Scanner(System.in);
		// try (Scanner scanner = new Scanner(System.in)){
		try{
			System.out.print("> 메뉴 선택하세요 ? ");
			selectedNumber = scanner.nextInt();
			scanner.nextLine(); // \r\n 제거 작업
		} catch (Exception e) {      }

	}

	private static void 메뉴출력() {
		System.out.printf("[메뉴]\n");
		for (int i = 0; i < menu.length; i++) {
			System.out.printf("%d. %s\n", i+1, menu[i]);
		}
	}

	
} // class



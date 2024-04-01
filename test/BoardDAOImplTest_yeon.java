package test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.util.DBConn;

import days05.board.domain.BoardDTO;
import days05.board.persistence.BoardDAO;
import days05.board.persistence.BoardDAOImpl;
import domain.EmpVO;
import persistence.EmpDAO;
import persistence.EmpDAOImpl;

public class BoardDAOImplTest_yeon { // 단위테스트 실시중요! 내가만듦
/*
	@Test
	void testselect() {
		Connection conn = DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);		
		
		try {
			ArrayList<BoardDTO> list = dao.select();
			// 출력 확인
			System.out.println("게시판수 : " + list.size() + "개");
		} catch (SQLException e) {
						e.printStackTrace();
		}

		
		DBConn.close();


	}

//	@Test
//	   void testAddboard() {      
//	      Connection conn = DBConn.getConnection();
//	      BoardDAO dao = new BoardDAOImpl(conn);   
//	      
//	      // java.sql.Date hireDate = java.sql.Date.valueOf("1992-02-25");
//	      
//	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//	      Date hiredate = null;
//	      try {
//	         hiredate = (Date) sdf.parse("2017-01-01");
//	      } catch (ParseException e) { 
//	         e.printStackTrace();
//	      }
//	      
//	      BoardDTO dto = new EmpVO( 9999 , "동영", "SALES", 7369, hiredate, 1000.0, 100.0, 40);
//	      int rowCount = dao.insert(dto);   
//	      // 출력 확인
//	      if( rowCount == 1) System.out.println("사원 추가 성공!!!");      
//	      DBConn.close();      
//	   }
//
//}

*/
}

package days05;

import java.sql.Connection;

import com.util.DBConn;

import days05.board.controller.BoardController;
import days05.board.persistence.BoardDAO;
import days05.board.persistence.BoardDAOImpl;
import days05.board.service.BoardService;

/**
 * @author 조연화
 * @date 2024. 3. 20. 오후 4:03:33 
 * @subject	검색파트 => 내일 테스트!!!!!!
 * @content	
 */
public class Ex02 {

	public static void main(String[] args) {
		// [페이징 처리]
		// 1) 현재 페이지 번호
		// 2) 한 페이지에 출력할 게시글의 수 ? 
		// 3) 페이징 블럭 갯수 ? 
		
		Connection conn = DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);
		BoardService service = new BoardService(dao);
		BoardController controller = new BoardController(service);

		controller.boardStart();
		
	}// main

}// class

// 검색결과 + 페이징처리
// 2번 페이지 -> 검색결과 + 페이징처리
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
public class Ex01 {

	public static void main(String[] args) {
		// [1] 게시판 패키지 선언
		// days05
		//   ㄴ board
		//		  ㄴ domain  	- DTO, VO
		//					BoardDTO.java
		//		  ㄴ persistence - DAO
		//					BoardDAO.java 인터페이스
		//					BoardDAOImpl.java 클래스
		//		  ㄴ service 	- o
		//		  ㄴ controller	- 

		// [2] 
		/*
		-- Oracle 수정
		CREATE TABLE tbl_cstVSBoard (
			 seq NUMBER NOT NULL PRIMARY KEY , -- 글 일련번호(PK)
			 writer VARCHAR2(20) NOT NULL , -- 작성자
			 pwd VARCHAR2(20) NOT NULL , -- 비밀번호
			 email VARCHAR2(100) , -- 이메일
			 title VARCHAR2(200) NOT NULL , -- 제목
			 writedate DATE DEFAULT SYSDATE, -- 작성일
			 readed NUMBER DEFAULT 0 , -- 조회수
			 tag NUMBER(1) NOT NULL ,
			 content CLOB
		);
		-- Table TBL_CSTVSBOARD이(가) 생성되었습니다.
		create sequence seq_tbl_cstvsboard
		nocache;
		--Sequence SEQ_TBL_CSTVSBOARD이(가) 생성되었습니다.
		*/
		
		
		/*
		 * 							  ->   BoardDTO   ㄱ
		 * 사용자(USER) -> BoardController.java -> BoardService.java
		 * 				  *[게시글 쓰기] : 작성				ㄴ int insertService()	 BoardDTO
		 * 					 return int ?
		 * 						 수정							1) DB처리	   -> BoardDAOImpl.java  -> 오라클 DB서버연동 
		 * 						 삭제							2) 로그기록		 ㄴ int insert(dto)
		 * 						 상세보기						3)   ?
		 * 						 BoardDAO
		 * 						 목록보기()					selectService()  -> ArrayList<> service();
		 * 						 메뉴 선택					1+2+3) 논리적인 처리작업
		 * 												 커밋, 롤백(트랜잭션처리)
		 * */
		// DAO -> 특정역할만 수행하도록 역할부여(규모가 커질수록 업무분담이 좋으니까)
		
		// 목록, 쓰기, 삭제, 수정, [검색]
		// BoardDAO, BoardDAOImpl, BoardService <- BoardController
		Connection conn = DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);
		BoardService service = new BoardService(dao);
		BoardController controller = new BoardController(service);

		controller.boardStart();
		
	}// main

}// class

package days05.board.persistence;

import java.sql.SQLException;
import java.util.ArrayList;

import days05.board.domain.BoardDTO;

public interface BoardDAO {

	// 1. 게시글 목록 조회 + 페이징 처리 X
	ArrayList<BoardDTO> select() throws SQLException;
	// 1-2. 게시글 목록 조회 + 페이징 처리 O
	ArrayList<BoardDTO> select(int currentPage, int numberPerPage) throws SQLException;
	
	// 2. 게시글 쓰기
	int insert(BoardDTO dto) throws SQLException;
	
	// 3. 상세 보기 - 조회수증가
	int increaseReaded(long seq) throws SQLException;
	
	// 3-2. 상세보기
	BoardDTO view(long seq) throws SQLException;
	
	// 4. 게시글 삭제
	int delete(long seq) throws SQLException;
	
	// 5. 게시글 수정
	//int update(long seq, String title, String content, String email ) throws SQLException;
	int update(BoardDTO dto) throws SQLException;

	// 6. 게시글 검색 추상메서드
	ArrayList<BoardDTO> search(int searchCondition, String searchWord) throws SQLException;
	
	// 6-2. 게시글 검색 + 페이징 처리 O
	ArrayList<BoardDTO> search(int searchCondition, String searchWord
			, int currentPage, int numberPerpage) throws SQLException;
	
	// 총 레코드 수
		int getTotalRecords(  ) throws SQLException;
	// 총 페이지 수 반환하는 메서드
		int getTotalPages( int numberPerPage ) throws SQLException;
	// 검색된 총 페이지 수 
		int getTotalPages(int numberPerpage, int searchCondition, String searchWord) throws SQLException;
	
}//interface




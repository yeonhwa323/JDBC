package days05.board.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBConn;

import days05.board.domain.BoardDTO;
import days05.board.service.BoardService;

public class BoardController {

	private Scanner scanner = null;
	private int selectedNumber ;
	private BoardService service ;

	// [ 페이징 처리 필요한 필드 선언 ]
	private int currentPage = 1;
	private int numberPerpage = 10;
	// [1] 2 3 4 5 6 7 8 9 10 >
	private int numberOfPageBlock = 10;


	public BoardController() {
		super();
		this.scanner = new Scanner(System.in);
	}

	// 1. 생성자를 통한 DI
	public BoardController(BoardService service) {
		this();
		this.service = service;
	}

	// 게시판 기능을 사용...
	public void boardStart() {
		while (true) {
			메뉴출력();
			메뉴선택();
			메뉴처리();
		}// while
	}

	private void 메뉴처리() {
		switch (this.selectedNumber) {
		case 1:// 새글   
			새글쓰기();
			break;
		case 2:// 목록
			목록보기();
			break;
		case 3:// 보기
			상세보기();
			break;
		case 4:// 수정
			수정하기();
			break;
		case 5:// 삭제
			삭제하기();
			break;
		case 6:// 검색
			검색하기();
			break;
		case 7:// 종료   
			exit();
			break; 
		} // switch

		일시정지();

	}


	private void 검색하기() {
		System.out.print(
				"> 검색 조건 : 제목(1) , 내용(2), 작성자(3), 제목+내용(4) 선택  ? ");
		int searchCondition = this.scanner.nextInt();
		System.out.print("> 검색어 입력 ? ");
		String searchWord = this.scanner.next();

		// 목록보기 코딩 복붙해서 수정
		System.out.print("> 현재 페이지번호를 입력 ? ");
		this.currentPage = this.scanner.nextInt();

		ArrayList<BoardDTO> list = 
				this.service.searchService(
						searchCondition
						, searchWord
						, this.currentPage
						, this.numberPerpage
						);

		// 뷰(View)-출력담당
		System.out.println("\t\t\t 게시판");
		System.out.println("----------------------------------------------------------");
		System.out.printf("%s\t%-40s\t%s\t%-10s\t%s\n"
				,"글번호", "글제목", "글쓴이", "작성일", "조회수");
		System.out.println("----------------------------------------------------------");
		if (list == null) {
			System.out.println("\t\t> 게시글 존재 X ");
		} else {
			Iterator<BoardDTO> ir = list.iterator();
			while (ir.hasNext()) {
				BoardDTO dto = ir.next();
				System.out.printf("%d\t%30s  %s\t%-10s\t%d\n",
						dto.getSeq(),
						dto.getTitle(),
						dto.getWriter(),
						dto.getWritedate(),
						dto.getReaded() );
			}// while
		}
		System.out.println("------------------------------------------------------");
		String pageblock = this.service.pageService(
				currentPage, numberPerpage, numberOfPageBlock
				, searchCondition, searchWord ) ;
		System.out.println(pageblock); 
		System.out.println("------------------------------------------------------");

	}

	private void 삭제하기() {
		System.out.print("> 삭제하는 글번호를 입력 ? ");
		int seq = this.scanner.nextInt();
		int rowCount = this.service.deleteService(seq);
		if (rowCount==1) {
			System.out.println("> 게시글 삭제 완료!!!");
		}
	}

	private void 수정하기() {
		System.out.print("> 수정할 글번호를 입력 ? ");
		int seq = this.scanner.nextInt();
		// 1. 원래 게시글 정보를 출력. - 문제점) 조회수 증가 시키더라.
		//BoardDTO dto = this.service.viewService(seq);

		BoardDTO dto = null;

		try {
			dto = this.service.getDao().view(seq);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (dto == null) {
			System.out.println("> 보고자하는 게시글이 존재 X");
			return;
		}// if

		// 뷰(View) : 출력 담당 객체
		System.out.println("\tㄱ. 글번호 : " + seq );
		System.out.println("\tㄴ. 작성자 : " + dto.getWriter() );
		System.out.println("\tㄷ. 조회수 : " + dto.getReaded() );
		System.out.println("\tㄹ. 글제목 : " + dto.getTitle() );
		System.out.println("\tㅁ. 글내용 : " + dto.getContent() );
		System.out.println("\tㅂ. 작성일 : " + dto.getWritedate() );

		// 2. 수정할 값 입력 받는 코딩.
		System.out.print("> 1. 이메일 입력 ? ");
		String email = scanner.next();
		System.out.print("> 2. 제목 입력 ? ");
		String title = scanner.next();
		System.out.print("> 3. 내용 입력 ? ");
		String content = scanner.next();

		// 수정할 값 
		dto = BoardDTO.builder()
				.seq(seq)
				.email(email)
				.title(title)
				.content(content)
				.build();

		int rowCount = this.service.updateService(dto);	

		if (rowCount == 1) {
			System.out.printf("> %d번 게시글 수정 완료!!\n", seq);
		}
	}

	private void 상세보기() {
		System.out.print("> 보고자하는 글번호를 입력 ? ");
		int seq = this.scanner.nextInt();

		BoardDTO dto = this.service.viewService(seq);

		if (dto == null) {
			System.out.println("> 보고자하는 게시글이 존재 X ");
			return;
		}// if

		// 뷰(View) : 출력 담당 객체
		System.out.println("\tㄱ. 글번호 : " + seq );
		System.out.println("\tㄴ. 작성자 : " + dto.getWriter() );
		System.out.println("\tㄷ. 조회수 : " + dto.getReaded() );
		System.out.println("\tㄹ. 글제목 : " + dto.getTitle() );
		System.out.println("\tㅁ. 글내용 : " + dto.getContent() );
		System.out.println("\tㅂ. 작성일 : " + dto.getWritedate() );

		System.out.println("\t\n [수정] [삭제] [목록(home)]");

		일시정지();

	}

	private void 목록보기() {
		System.out.print("> 현재 페이지번호를 입력 ? ");
		this.currentPage = this.scanner.nextInt();

		ArrayList<BoardDTO> list = 
				this.service.selectService(this.currentPage, this.numberPerpage);

		// 뷰(View)-출력담당
		System.out.println("\t\t\t  게시판");
		System.out.println("-------------------------------------------------------------------------");
		System.out.printf("%s\t%-40s\t%s\t%-10s\t%s\n", 
				"글번호","글제목","글쓴이","작성일","조회수");
		System.out.println("-------------------------------------------------------------------------");
		if (list == null) {
			System.out.println("\t\t> 게시글 존재 X");
		} else {
			Iterator<BoardDTO> ir = list.iterator();
			while (ir.hasNext()) {
				BoardDTO dto = ir.next();
				System.out.printf("%d\t%-30s  %s\t%-10s\t%d\n",
						dto.getSeq(), 
						dto.getTitle(),
						dto.getWriter(),
						dto.getWritedate(),
						dto.getReaded());  

			}// while
		}
		System.out.println("-------------------------------------------------------------------------");
		String pageblock = this.service.pageService(currentPage, numberPerpage, numberOfPageBlock) ;
		System.out.println(pageblock); 
		System.out.println("-------------------------------------------------------------------------");

	}

	private void 새글쓰기() {
		System.out.print("> writer, pwd, email, title, tag, content 입력 ? ");
		String [] datas = this.scanner.nextLine().split("\s*,\s*");

		String writer = datas[0];
		String pwd = datas[1];
		String email = datas[2];
		String title = datas[3];
		int tag = Integer.parseInt(datas[4]);
		String content = datas[5];

		BoardDTO dto = BoardDTO.builder()
				.writer(writer)
				.pwd(pwd)
				.email(email)
				.title(title)
				.tag(tag)
				.content(content)
				.build();
		int rowCount = this.service.insertService(dto);

		if (rowCount == 1) {
			System.out.println("> 새글 쓰기 완료!!!!");
		}

	}

	private void 메뉴선택() {
		System.out.print("> 메뉴 선택하세요 ? ");	    
		this.selectedNumber = this.scanner.nextInt(); 			
		this.scanner.nextLine(); // \r\n 제거
	}

	private void 메뉴출력() {
		String [] menu = {"새글", "목록", "보기", "수정", "삭제", "검색", "종료" };
		System.out.println("[ 메뉴 ]");
		for (int i = 0; i < menu.length; i++) {
			System.out.printf("%d. %s\t", i+1, menu[i]);
		}
		System.out.println();

	}

	private void 일시정지() {
		System.out.println(" \t\t 계속하려면 엔터치세요.");
		try {
			System.in.read();
			System.in.skip(System.in.available()); // 13, 10
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

	// 프로그램 종료
	private void exit() {
		DBConn.close();
		System.out.println("\t\t\t  프로그램 종료!!!");
		System.exit(-1);
	}


}// class

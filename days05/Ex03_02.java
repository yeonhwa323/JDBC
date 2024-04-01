package days05;

public class Ex03_02 {

	public static void main(String[] args) {
		//int currentPage = 1;
		int numberPerpage = 10;
		int numberOfPageBlock = 10;
		
		// currentPage = 1 	[1] 2 3 4 5 6 7 8 9 10 >
		// currentPage = 2 	1 [2] 3 4 5 6 7 8 9 10 >
		// currentPage = 3 	1 2 [3] 4 5 6 7 8 9 10 >
		// currentPage = 4 	1 2 3 [4] 5 6 7 8 9 10 >
		// currentPage = 5 	1 2 3 4 [5] 6 7 8 9 10 >
		// currentPage = 6 	1 2 3 4 5 [6] 7 8 9 10 >
		//				   :
		// currentPage = 11  <[11] 12 13 14 15 16
		// currentPage = 12  <[11] 12 13 14 15 16
		// currentPage = 13  <[11] 12 13 14 15 16
		// currentPage = 14  <[11] 12 13 14 15 16
		// currentPage = 15  <[11] 12 13 14 15 16
		// currentPage = 16  <[11] 12 13 14 15 16
		
		// 1) 총 게시글 수 ? 152
		// 2) 총 페이지 수 ? 16
		for (int currentPage = 1; currentPage <= 34; currentPage++) {
			int start = (currentPage-1)/numberOfPageBlock*numberOfPageBlock+1;
			int end = start + numberOfPageBlock -1;
			if (end > 34) end = 34;
			
			System.out.printf("currentPage =%d ", currentPage );
			
			if( start != 1 ) System.out.printf(" < ");
			for (int i = start; i <= end; i++) {
				if( i == currentPage ) System.out.printf("[%d] ", i);
				else System.out.printf("%d ", i);
			}
			if( end != 34 ) System.out.printf(" > ");
			System.out.println();
		}
		
		
		
	}// main

}// class

package days05;

public class Ex03 {

	public static void main(String[] args) {
		// int currentPage = 1;
		int numberPerpage = 15;
		
		// WHERE no BETWEEN start AND end
		int start = 1, end = 10;
		for (int currentPage = 1; currentPage <= 5; currentPage++) {
			start = (currentPage-1)*numberPerpage +1;
			end = start + numberPerpage-1;
			
			System.out.printf(
					"currentPage=%d no BETWEEN %d AND %d \n"
					, currentPage
					, start
					, end );
		} 

	}

}

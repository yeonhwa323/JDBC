package persistence;

import java.util.ArrayList;

import domain.EmpVO;

public interface EmpDAO { // DB처리하는 역할수행

	// 1. 모든 사원정보 조회 추상메서드
	public abstract ArrayList<EmpVO> getEmpSelect();

	// 2. 사원 추가 추상메서드
	int addEmp(EmpVO vo);

	// 3. 사원 수정
	int updateEmp(EmpVO vo);

	// 4. 사원 삭제
	int deleteEmp(int empno);
	//int deleteEmp(int [] empnos);
	//int deleteEmp(ArrayList<Integer> empnos);

	// 5. 사원 검색
	ArrayList<EmpVO> searchEmp(int searchCondition, String searchWord);

	// 6. 한 사원 정보
	EmpVO getEmp(int empno);

}// class


// https://blog.naver.com/tiamet133/222842271972 (DTO/DAO 네이버검색블로그 참고)
package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// [V]alue[O]bject
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DeptVO {
	
	private int deptno;
	//@NonNull
	private String dname;
	private String loc;
	
	private int cnt; // 필드 추가

	public DeptVO(int deptno, String dname, String loc) {
		
		this.deptno = deptno;
		this.dname = dname;
		this.loc = loc;
	}


	
	
	
	}

	



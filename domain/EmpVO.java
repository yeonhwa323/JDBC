package domain;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//[V]alue[O]bject
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpVO {	
	
	private int empno;
	private String ename; 
	private String job; 
	private int mgr; 
	
	//private String hiredate;
	private Date hiredate;

	private Double sal;
	private Double comm;
	private int deptno;
	
}

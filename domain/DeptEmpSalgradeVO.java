package domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeptEmpSalgradeVO {
	
	private int empno;
	private String dname;
	private String ename;
	private Date hiredate; // java.util.date
	private Double pay;
	private int grade;

}

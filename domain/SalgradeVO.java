package domain;

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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalgradeVO {	
	
	private int grade;
	private int losal;
	private int hisal;
	
	private int cnt; // 추가
	
}

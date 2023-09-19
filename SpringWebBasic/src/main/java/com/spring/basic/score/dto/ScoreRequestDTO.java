package com.spring.basic.score.dto;

import lombok.*;

/*
 DTO (Data Trasfer Object): 데이터 전송(이동) 객체 라는 의미
 - 계층간(3티어) 데이터 교환을 위한 객체.
 - 로직을 갖고 있지 않은 순수한 데이터 객체로 활용. getter / setter 메서드만 갖는다.
 - 사용 빈도 多
 
 VO (Value Object): 좀 더 특정한 결과 값을 담는 객체.
 값 자체를 표현하기 때문에 객체의 불변성을 보장해야 하면 setter 메서드는 갖지 않는 것을 권장합니다.
 바뀌지 않는 순수한 값들을 담는다.
 객체가 생성이 될 때 값이 초기화 되고 그 값만을 담게 만듬.
 
 */

@Getter @Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ScoreRequestDTO {
	
	//점수를 등록해 달라고 요청이 들어올 때만! 사용하는 메서드!
	
	private String name;//학생 이름
	private int kor, eng, math; // 국, 영, 수 점수

}

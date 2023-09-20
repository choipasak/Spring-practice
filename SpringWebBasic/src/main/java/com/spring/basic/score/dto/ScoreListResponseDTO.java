package com.spring.basic.score.dto;

import com.spring.basic.score.entity.Grade;
import com.spring.basic.score.entity.Score;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/*
Entity는 요청이나 응답을 담는 값으로 쓰지 말라 했죠?
데이터 베이스에서 조회된 값을 화면으로 응답할 때, 해당 화면이 무엇이냐에 따라
조회된 값을 가공하거나 추가하거나 제외하고 전달해야 할 필요성이 있기 때문에
그에 맞는 응답 DTO를 생성해서 전달하는 것이 각자의 역할을 침해하지 않는 설계일 것입니다.
*/
@Getter @ToString @EqualsAndHashCode // 엄격하게 관리하기 위해서 이렇게만 해준다.
public class ScoreListResponseDTO {
	
	//어떤 정보가 나갈 지 모르니까 Entity전부를 보내줄 이유가 없음
	//그래서 요청에 맞는 값을 응답해주기 위해서 응답용 DTO를 따로 만들어 준 것임.
	
	private int stuNum;
	private String maskingName;
	private double average;
	private Grade grade; //이렇게 4개만 응답으로 보낼것임
	
	public ScoreListResponseDTO(Score s) {
		this.stuNum = s.getStuNum();
		this.maskingName = makeMaskingName(s.getStuName());//DTO만들 때, 날것의 이름말고 *처리된 이름으로 만들어주는 메서드를 통해서 이름을 저장하겠다.
		this.average = s.getAverage();
		this.grade = s.getGrade();
	}

	
	//이름에서 첫 글자(성)만 빼고 나머지를 *로 처리하기
	private String makeMaskingName(String originalName) {
		
		String maskingName = String.valueOf(originalName.charAt(0));//첫글자 뽑아내기(char)
		//valueOf(): 다른 타입을 문자열로 변환하는 메서드
		
		for(int i=1; i<originalName.length(); i++) {//i=1 -> 첫 문자(성) 빼고부터 시작한다
			maskingName += "*";
		}
		return maskingName;
	}
}

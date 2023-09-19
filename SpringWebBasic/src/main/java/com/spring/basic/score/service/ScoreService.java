package com.spring.basic.score.service;

import org.springframework.stereotype.Service;

import com.spring.basic.score.dto.ScoreRequestDTO;

//컨트롤러와 레파지토리 사이에 배치되어 기타 비즈니스 로직 처리
//ex) 값을 가공, 예외 처리, dto로 변환, 트랜잭션 등등 ...

@Service //서비스 계층은 이렇게 빈 등록 해준다! <- @Controller와 다른점: 없음. 이름만 다름.
public class ScoreService {

	//score에서 받은 점수로 총합점과 평균을 내는 서비스계층
	//등록 중간처리
	//레파지토리에 데이터를 넘기기 전에 가공한다.
	public void insertScore(ScoreRequestDTO dto) {//데이터 타입으로 boolean으로 하는 사람도 있다!

		/*
		 DTO (Data Trasfer Object): 데이터 전송(이동) 객체 라는 의미
		 - 계층간(3티어) 데이터 교환을 위한 객체.
		 - 로직을 갖고 있지 않은 순수한 데이터 객체로 활용. getter / setter 메서드만 갖는다.
		 - 사용 빈도 多
		이어서 제대로 모든 정보를 여기 메서드에서 가공하려면 entity가 필요함
		 */
		

	}


}

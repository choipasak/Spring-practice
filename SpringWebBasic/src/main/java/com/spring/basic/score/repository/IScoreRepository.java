package com.spring.basic.score.repository;

import java.util.List;

//@Component -> 3계층 이외의 객체를 빈으로 등록할 때 사
import com.spring.basic.score.entity.Score;

// 역할 명세(추상화):
// 성적 정보를 잘 저장하고, 잘 조회하고, 잘 삭제하고, 잘 수정해야 한다.
// Score에 관련된 여러가지 동작들을 명세하여 구현하는 클래스들이
// 동일한 동작을 약속하게 하자!
public interface IScoreRepository {

	
	//성적 정보 전체 조회
	List<Score> findAll();// Entity전부를 담아야 하니까 List
	
	//성적 정보 등록
	void save(Score score);
	
	//성적 정보를 개별 조회하는 메서드
	Score findByStuNum(int stuNum);
	// Score가 타입인 이유: 한명의 정보를 보내는 것이니까!

	//성적 정보 삭제 메서드
	void deleteByStuNum(int stuNum);
	
	/*
	 * 내가 한 버전 post-modify용도
	//성적 정보 재등록
	void resave(Score score, int stuNum);
	 */

	void modify(Score modScore);
	

}









































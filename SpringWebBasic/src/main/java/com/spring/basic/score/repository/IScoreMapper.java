package com.spring.basic.score.repository;

import java.util.List;

import com.spring.basic.score.entity.Score;

public interface IScoreMapper {
	
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

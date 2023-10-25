package com.spring.myweb.snsboard.mapper;

import java.util.List;
import java.util.Map;

import com.spring.myweb.freeboard.dto.page.Page;
import com.spring.myweb.snsboard.entity.SnsBoard;

public interface ISnsBoardMapper {
	
	//등록
	void insert(SnsBoard board);
	
	//목록
	List<SnsBoard> getList(Page page);
	
	//상세보기
	SnsBoard getDetail(int bno);
	
	//삭제
	void delete(int bno);
	
	//좋아요 탐색
	int searchLike(Map<String, String> params); //마이바티스는 매개 값을 2개 이상 받을 수 없다 -> Map에 값을 다 받아서 받기 선택!
	
	//좋아요 등록
	void createLike(Map<String, String> params);
	
	//좋아요 삭제
	void deleteLike(Map<String, String> params);

	//특정 회원의 좋아요 글 번호 목록
	List<Integer> likeList(String userId);
}

package com.spring.basic.board.repository;

import java.util.List;

import com.spring.basic.board.entity.Board;

public interface IBoardMapper {
	
	//sql생각하면 추상메서드 쓰는 것 어렵지 않음
	//sql로 기능에 사용해야 할 명령문 + 리턴 받을 값이 있는지 + 매개 값으로 필요한 값이 무엇인지
	
	//게시글 등록
	void insertArticle(Board board);
	
	//게시글 목록
	List<Board> getArticles();
	
	//게시글 상세
	Board getArticle(int bno);
	
	//게시글 수정
	void updateArticle(Board board);
	
	//게시글 삭제
	void deleteArticle(int bno);
	
}

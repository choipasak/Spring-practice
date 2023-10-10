package com.spring.basic.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.basic.board.dto.BoardListResponseDTO;
import com.spring.basic.board.dto.BoardModifyRequestDTO;
import com.spring.basic.board.entity.Board;
import com.spring.basic.board.repository.IBoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	//Mapper.xml와의 의존성 부여
	private final IBoardMapper mapper;

	// 게시글 작성
	public void insertArticle(String writer, String title, String content) {
		
		Board board = new Board();
		board.setWriter(writer);
		board.setTitle(title);
		board.setContent(content);
		
		mapper.insertArticle(board);
		
	}
	
	// 글 전체 목록 요청 -> 응답용 DTO로 리턴
	public List<BoardListResponseDTO> getArticles() {
		
		//응답용 DTO 생성
		List<BoardListResponseDTO> dtoList = new ArrayList<>();
		
		List<Board> boardList = mapper.getArticles();
		
		for(Board b : boardList) {
			BoardListResponseDTO dto = new BoardListResponseDTO(b);
			dtoList.add(dto);
		}
		
		return dtoList;
	}

	// 상세보기(글 하나), 수정후 페이지(수정한 글의 상세보기페이지)
	public Board retrieve(int boardNo) { // 원래 Board로 바로 주지 않고 응답DTO로 리턴해야함
		return mapper.getArticle(boardNo);
	}
	/*
	public void updateArticle(String writer, String title, String content) {
		
		Board board = new Board();
		board.setWriter(writer);
		board.setTitle(title);
		board.setContent(content);
		
		mapper.updateArticle(board);
		
	}
	*/
	// 게시글 수정하기
	public void modify(BoardModifyRequestDTO dto) {
		
//		Board board = Board.builder()
//						   .boardNo(dto.getBoardNo())
//						   .writer(dto.getWriter())
//						   .title(dto.getTitle())
//						   .content(dto.getContent())
//						   .build();
		// Builder를 만든다면 이렇게 원하는 값만 메서드체이닝으로 값을 뽑아서 board객체에 담으면 끝!(객체 생성, setter, 등등을 작성하지 않아도 되게 되었다!)
		// 원하는 값으로만 초기화 된 구성으로 이루어진 객체를 얻을 수 있다. 
		
		mapper.updateArticle(Board.builder()
								   .boardNo(dto.getBoardNo())
								   .writer(dto.getWriter())
								   .title(dto.getTitle())
								   .content(dto.getContent())
								   .build());
	}

	
	// 게시글 지우기
	public void deleteArticle(int boardNo) {
		mapper.deleteArticle(boardNo);
	}


	
	
	
	
}

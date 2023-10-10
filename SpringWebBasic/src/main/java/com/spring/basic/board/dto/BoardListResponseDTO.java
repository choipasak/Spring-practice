package com.spring.basic.board.dto;

import java.time.LocalDateTime;

import com.spring.basic.board.entity.Board;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString @EqualsAndHashCode
// 서비스계층에서 사용할 content값을 뺀 Board의 정보 응답용 DTO
public class BoardListResponseDTO {

	private int boardNo;
	private String writer;
	private String title;
	private LocalDateTime regDate;
	
	
	public BoardListResponseDTO(Board b) {
		this.boardNo = b.getBoardNo();
		this.writer = b.getWriter();
		this.title = b.getTitle();
		this.regDate = b.getRegDate();
	}
	
}

package com.spring.myweb.freeboard.DTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.spring.myweb.freeboard.entity.FreeBoard;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString @EqualsAndHashCode
public class FreeContentResponseDTO {
	
	private int bno;
	private String title;
	private String writer;
	private String content;
	private String date;
	
	
	public FreeContentResponseDTO(FreeBoard board) {
		super();
		this.bno = board.getBno();
		this.title = board.getTitle();
		this.writer = board.getWriter();
		this.content = board.getContent();
		if(board.getUpdateDate() == null) {
			this.date = makeDate(board.getRegDate());
		}else {
			this.date = makeDate(board.getUpdateDate());
		}
	}
	
	private String makeDate(LocalDateTime date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return dtf.format(date);
	}
	
	

}

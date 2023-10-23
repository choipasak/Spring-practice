package com.spring.myweb.snsboard.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SnsBoard {
	
	private int bno;
	private String writer;
	private String uploadPath;
	private String fileLoca; //폴더명
	private String fileName; //UUID 파일명
	private String fileRealName; //원본파일명
	private String content;
	private LocalDateTime regDate;

}

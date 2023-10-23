package com.spring.myweb.snsboard.dto;

import org.springframework.web.multipart.MultipartFile;

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
public class SnsBoardRequestDTO {
		
	//화면단에서 파일의 정보를 담은 formData를 받아와서 서버에 저장하려는 DTO
	
	private String content;
	private String writer;
	private MultipartFile file;
}

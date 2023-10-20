package com.spring.myweb.reply.dto;

import com.spring.myweb.reply.entity.Reply;

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
public class ReplyRequestDTO {
	
	//JS에서 작성해 준 값(property)의 변수명과 똑같이 맞춰야 값을 JSON에서 가져올 수 있음.
	private int bno;
	private String replyText;
	private String replyId;
	private String replyPw;
	
	public Reply toEntity(ReplyRequestDTO dto) {
		return Reply.builder()
			 .bno(dto.getBno())
			 .replyText(dto.getReplyText())
			 .replyWriter(dto.getReplyId())
			 .replyPw(dto.getReplyPw())
			 .build();
	}
	
	
}

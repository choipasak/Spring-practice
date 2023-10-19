package com.spring.myweb.reply.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.myweb.freeboard.dto.page.Page;
import com.spring.myweb.reply.dto.ReplyListResponseDTO;
import com.spring.myweb.reply.dto.ReplyRegistDTO;
import com.spring.myweb.reply.entity.Reply;
import com.spring.myweb.reply.mapper.IReplyMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService implements IReplyService {

	
	private final IReplyMapper mapper;
	private final BCryptPasswordEncoder encoder;
	
	
	@Override
	public void replyRegist(ReplyRegistDTO dto) {
		dto.setReplyPw(encoder.encode(dto.getReplyPw())); //비밀번호 암호화
		mapper.replyRegist(dto.toEntity(dto));
	}

	@Override
	public List<ReplyListResponseDTO> getList(int bno, int pageNum) {
		
		//1번 방법
		Page page = Page.builder()
						.pageNo(pageNum)
						.amount(5)
						.build();
		//page.setPageNo(pageNum);
		//page.setAmount(5); //댓글은 한 화면에 5개씩 보이게 설정함.
		
		//2번 방법
		Map<String, Object> map = new HashMap<>();
		map.put("paging", page); //Page객체 변수명 mapper에 써준것과 맞춰준 것임
		map.put("boardNo", bno); //Page객체 #{}안에 작성한 값의 변수명과 맞춰준 것임
		
		System.out.println("글 목록 가져오기 전임");
		
		List<ReplyListResponseDTO> dtoList = new ArrayList<>();
		//System.out.println("DB에서 불러온 댓글 목록: " + list);
		for(Reply reply : mapper.getList(map)) {
			dtoList.add(new ReplyListResponseDTO(reply));
			System.out.println("글넘어와유");
		}
		
		return dtoList;
	}

	@Override
	public int getTotal(int bno) {
		return mapper.getTotal(bno);
	}

	@Override
	public String pwCheck(int rno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Reply reply) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int rno) {
		// TODO Auto-generated method stub

	}

}

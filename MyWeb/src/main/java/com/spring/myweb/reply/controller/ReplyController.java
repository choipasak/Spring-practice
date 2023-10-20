package com.spring.myweb.reply.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.myweb.reply.dto.ReplyListResponseDTO;
import com.spring.myweb.reply.dto.ReplyRequestDTO;
import com.spring.myweb.reply.dto.ReplyUpdateRequestDTO;
import com.spring.myweb.reply.service.IReplyService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/reply")
@RequiredArgsConstructor
@RestController //비동기 컨트롤러
public class ReplyController {
	
	private final IReplyService service;	
	
	//댓글 등록 요청
	//화면에서 요청을 비동기 POST요청으로 /reply만 했는데 어떻게 받게 될까 ->
	@PostMapping()
	public String replyRegist(@RequestBody ReplyRequestDTO dto) {
		
		System.out.println("댓글 등록 요청 들어옴!" + dto);
		service.replyRegist(dto);
		
		return "regSuccess";
	}
	
	//댓글 목록 요청!
	@GetMapping("/list/{bno}/{pageNum}")
	public Map<String, Object> getList(@PathVariable int bno, @PathVariable int pageNum) { //@PathVariable: 경로에 붙은 번호르 매핑에{}로 작성. 저 값을 가져온단 뜻!
		
		/*
		 1. 화면단에서 getList 라는 메서드가 글 번호, 페이지 번호를 url을 통해 전달합니다. -> bno, pageNum
		 2. Mapper 인터페이스에게 복수의 값을 전달하기 위해 Map을 쓸지, @Param을 쓸 지 결정.
		 3. ReplyMapper.xml에 sql문을 페이징 쿼리로 작성.
		 4. 클라이언트 측으로 DB에서 조회한 댓글 목록을 보낼 때,
			페이징을 위한 댓글의 총 개수도 함께 보내줘야 합니다.
		 5. 근데 우리는 return을 한 개만 쓸 수 있으니까, 복수 개의 값(목록, 총 댓글 개수 2개)을 리턴하기 위해
		 	리턴 타입을 Map으로 줄 지, 객체를 디자인해서 줄 지를 결정합니다.
		 	(댓글 목록 리스트와 전체 댓글 개수를 함께 전달할 예정) -> 일회성으로 쓸 거니까 Map으로 클라에게 전달.
		 */
		
		System.out.println("댓글 목록 요청이 들어옴! -> " + bno + "/" + pageNum);
		List<ReplyListResponseDTO> list = service.getList(bno, pageNum); //댓글 목록
		System.out.println("글 목록 받은 컨트롤러");
		int total = service.getTotal(bno); //게시글에 달려있는 댓글의 총 개수.
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("total", total);
		
		return map;
	}
	
	//댓글 수정 요청
	@PutMapping("/{rno}")
	public String update(@PathVariable int rno, @RequestBody ReplyUpdateRequestDTO dto) {
		dto.setReplyNo(rno);
		System.out.println(dto.getReplyPw());
		return service.update(dto); //service단에서 비번 검증해서 리턴 해 주기 때문에 바로 리턴
	}
	
	//댓글 삭제 요청
	@DeleteMapping("/{rno}")
	public String delete(@PathVariable int rno, @RequestBody String replyPw) {
		System.out.println("서버에서 받은 비밀번호-> " + replyPw);
		return service.delete(rno, replyPw);
	}
	
}



















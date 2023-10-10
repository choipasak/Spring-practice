package com.spring.basic.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.basic.board.dto.BoardModifyRequestDTO;
import com.spring.basic.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board") //url의 맨 뒤에 /board로 요청이 들어오면 연결
@RequiredArgsConstructor // 생성자를 이용해 주입
public class BoardController {
	
	//의존성 부여(Service 계층과)
	private final BoardService service;
	
	// 글 작성 화면을 열어주는 메서드 (void 의미: /board/write를 치면 board파일에 write.jsp파일로 응답해주겠다)
	@GetMapping("/write")
	public void write() {
		System.out.println("/board/write: GET!");
	}
	
	
	// 글 등록 요청 메서드
	@PostMapping("/write")
	// 매개 값으로 값을 지금 3개 받으니까 그냥 받아도 되지만, 받을 값이 많아지면 DTO를 선언해서 받아준다.
	public String write(String writer, String title, String content) {
		System.out.println("/board/write: POST!"); // 요청 진행 확인용 출력문
		service.insertArticle(writer, title, content);
		
		// 글 등록 완료 후 /board/list(글 목록) 요청이 다시 들어올 수 있게끔 redirect처리
		return "redirect:/board/list";
	}
	
	// 글 목록 화면 요청 메서드
	@GetMapping("/list")
	public void list(Model model) {
		System.out.println("/board/list: GET!");

		//Spring의 응답: Model
		model.addAttribute("articles", service.getArticles());
	}
	
	//글 내용 상세보기 요청 처리 메서드
	@GetMapping("/content")
	public void content(int boardNo, Model model){
		System.out.println("/board/content?boardNo= " + boardNo);
		retrieve(boardNo, model);
		
	}
	
    // 글 수정하기 화면으로 이동 요청
    // 메서드 이름은 modify(), url: /board/modify -> GET
    // 수정하고자 하는 글 정보를 모두 받아와서 modify.jsp로 보내 주세요.
	@GetMapping("/modify")
	public void modify(int boardNo, Model model) {
		System.out.println("/board/modify?boardNo= " + boardNo);
		retrieve(boardNo, model);
	}
    
    
    //modify.jsp를 생성해서, form태그에 사용자가 처음에 작성했던 내용이 드러나도록
    //배치해 주시고 수정을 받아 주세요.
    //수정 처리하는 메서드: modify(), 요청 url: /board/modify -> POST
    // 수정 처리 완료 이후 방금 수정한 글의 상세보기 요청이 다시 들어올 수 있도록 작성하세요.
    
	/*
	@PostMapping("/modify")
    public String modify(int boardNo, String writer, String title, String content, Model model) {
    	System.out.println("/board/modify: POST!");
    	model.addAttribute("modi",service.updateArticle(writer, title, content));
    	retrieve(boardNo, model);
    	return "redirect:/board/content?boardNo="+boardNo;
    }
	*/
	@PostMapping("/modify")
	public String modify(BoardModifyRequestDTO dto) {
		service.modify(dto);
		return "redirect:/board/content?boardNo=" + dto.getBoardNo();
	}
	
    
    //삭제는 알아서 작성해 주세요. (삭제 클릭하면 해당 글이 삭제될 수 있도록)
	@GetMapping("/delete")
	public String delete(int boardNo) { //@RequestParam이 생략 되어 있는 것을 잊지 말자!
		System.out.println("/board/delete: GET!");
		service.deleteArticle(boardNo);
		return "redirect:/board/list";
	}
	
	
	//상세보기, 수정 페이지 공통 로직을 메서드화
	private void retrieve(int boardNo, Model model) {
		model.addAttribute("article", service.retrieve(boardNo));
	}
	
}




























package com.spring.myweb.freeboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.myweb.freeboard.DTO.FreeRegistRequestDTO;
import com.spring.myweb.freeboard.service.IFreeBoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/freeboard")
@RequiredArgsConstructor
public class FreeBoardController {
	
	private final IFreeBoardService service;
	
	//목록 화면
	@GetMapping("/freeList")
	public void freeList(Model model){// Model객체에 목록을 담아서 보내야 함QQQQQQQQQQQQQQQQQQQQQQQQQQ -> A. jsp에 보내려고
		//void -> freeList의 페이지로 응답하겠다는 의미
		
		System.out.println("/freeboard/freeList: GET!");
		model.addAttribute("boardList", service.getList());// boardList라는 이름으로 List를 담아 보내겠다!
	}
	
	//글 등록 처리
	@PostMapping("/freeRegist")
	public String regist(FreeRegistRequestDTO dto) {
		service.regist(dto);
		return "redirect:/freeboard/freeList";
	}
	
	//글 상세보기
	@GetMapping("/content")
	public String content(int bno, Model model) {
		model.addAttribute("gocontent", service.getContent(bno));
		return "freeboard/freeDetail";
	}

}

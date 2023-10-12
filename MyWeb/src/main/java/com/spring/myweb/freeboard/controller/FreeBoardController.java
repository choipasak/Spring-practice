package com.spring.myweb.freeboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.myweb.freeboard.DTO.FreeRegistRequestDTO;
import com.spring.myweb.freeboard.DTO.FreeUpdateRequestDTO;
import com.spring.myweb.freeboard.entity.FreeBoard;
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
	
	//글 쓰기 페이지를 열어주는 메서드
	@GetMapping("/freeRegist")
	public void regist() {
	}
	
	//글 등록 처리
	@PostMapping("/freeRegist")
	public String regist(FreeRegistRequestDTO dto) {
		service.regist(dto);
		return "redirect:/freeboard/freeList";
	}
	
	//글 상세보기
	@GetMapping("/content")
	public String content(int bno, Model model) { //jsp에 상세보기글의 정보를 가져오니까 이걸 담은 model객체가 필요
		model.addAttribute("gocontent", service.getContent(bno)); // 포워드방식
		return "freeboard/freeDetail";
	}
	
	//글 수정 페이지로 이동 요청 -> POST
	@PostMapping("/modPage")
	public String modPage(@ModelAttribute("gocontent") FreeUpdateRequestDTO dto) {//@ModelAttribute("gocontent")의 정보를 dto에 꽃겠다.
		return "freeboard/freeModify";
	}
	
	//글 수정하기
	@PostMapping("/modify")
	public String modify(FreeUpdateRequestDTO dto) {
		service.update(dto);
		return "redirect:/freeboard/content?bno=" + dto.getBno();
	}
	
	//글 삭제하기
	@PostMapping("/delete")
	public String delete(int bno) {
		service.delete(bno);
		return "redirect:/freeboard/freeList";
	}
}

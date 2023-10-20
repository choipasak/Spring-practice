package com.spring.myweb.snsboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.spring.myweb.snsboard.service.SnsServiceBoard;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/snsboard")
@RequiredArgsConstructor
public class SnsBoardController {
	
	private final SnsServiceBoard service;
	
	@GetMapping("/snsList")
	public ModelAndView snsList() {
		ModelAndView mv = new ModelAndView();
		//mv.addAllObjects("name", "value");
		mv.setViewName("snsboard/snsList");
		return mv; //ModelAndView를 호출하면 비동기방식컨트롤러 안에서 동기방식인 원래대로 작동시킬 수 있다.
	};
	
	/*
	 정리: @RestController안에서 비동기 통신을 사용하지 않고 통신을 하고 싶다
	 	 -> 리턴 타입을 ModelAndView로 잡으면 된다.
	 */
	
	

}














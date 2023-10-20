package com.spring.myweb.rest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class RestControllerTest {

	@GetMapping("/view")
	public String viewPage() {
		return "test/test1";
	}
	
	/*
	 @RequestBody
	 - 클라이언트 쪽에서 전송하는 JSON 데이터를
	   서버에서 사용하는 자바 언어에 맞게 변환하여 받을 수 있는 아노테이션.
	 - import 필요!
	 
	 @ResponseBody
	 - 메서드가 리턴하는 데이터를 viewResolver에게 전달하지 않고,
	   클라이언트에게 해당 데이터를 바로 응답하게 합니다.
	   비동기 통신에서 주로 많이 사용합니다.
	   
	 @RestControllerTest 아노테이션을 통해 빈으로 등록한 컨트롤러는
	 모든 메서드가 리턴 값을 클라이언트로 직접 리턴합니다. (viewResolver를 사용하지 않습니다.)
	 REST 방식의 통신 전용 컨트롤러로 빈을 등록하는 것입니다.
	 */
	
	@PostMapping("/object")
	@ResponseBody
	public Person object(@RequestBody Person p) { //JSON으로 날라오는 데이터를 그냥 Person객체로 받을 수 있냐 -> X -> 라이브러리를 다운받았음 -> @RequestBody 사용
		System.out.println("비동기 방식의 요청 들어옴!");
		System.out.println(p.toString());
		
		p.setName("변경이름");
		p.setAge(100);
		
		return p;
		
		//요청 -> 비동기 방식
		//클라 페이지 -> 멈춰있음 -> 비동기통신 방법으로 데이터만 줄거임
		//중복체크 경우: 새로운 페이지로 응답하는게아니라 그냥 데이터만 주는 거!
		//어떻게 주냐: 메서드에 @ResponseBody 필요
		
	}

	
	////////////////////////////////////////////////////////////////////
	
	@GetMapping("/hello")
	public String hello() {
		return "hello world!";
	}
	
	@GetMapping("/hobby")
	public List<String> hobby() {
		return Arrays.asList("축구", "영화감상", "수영");
		//asList(): 전달한 문자로 List로 만들어줌
	}
	
	@GetMapping("/study")
	public Map<String, Object> study() {
		
		//JSON을 Map으로 전환
		Map<String, Object> subject = new HashMap<>();
		
		subject.put("자바", "java");
		subject.put("jsp", "spring");
		subject.put("spring", "spring framework");
		
		return subject;
		
	}
	
}




















package com.spring.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/hw")
public class LoginController {
	
	/*
    1번요청: 로그인 폼 화면 열어주기
    - 요청 URL : /hw/s-login-form : GET
    - 포워딩 jsp파일 경로:  /WEB-INF/views/response/s-form.jsp
    - html form: 아이디랑 비번을 입력받으세요.

    2번요청: 로그인 검증하기
    - 로그인 검증: 아이디를 grape111이라고 쓰고 비번을 ggg9999 라고 쓰면 성공
    - 요청 URL : /hw/s-login-check : POST
    - 포워딩 jsp파일 경로:  /WEB-INF/views/response/s-result.jsp
    - jsp에게 전달할 데이터: 로그인 성공여부, 아이디 없는경우, 비번 틀린경우
	- 로그인 여부를 "success", "f-pw", "f-id" 문자열로 전송

	s-result.jsp에서 전송된 로그인 여부를 확인해서 적절한 화면을 응답하시면 되겠습니다.
	응답 형태는 자유롭게 작성하세요.

	 */
	
	@GetMapping("/s-login-form")
	public String login() {
		System.out.println("/hw/s-login-form: GET요청 발생!");
		return "/response/s-form";
	}
	/*
	@PostMapping("/s-login-check")
	public ModelAndView check(Model model) {
		System.out.println("/hw/s-login-check: POST요청 발생!");
		ModelAndView mv = new ModelAndView();
		mv.addObject(model);
		
		if(!(mv.getViewName().equals("grape111")|| mv.getViewName().equals("ggg9999"))) {
			mv.addObject("f-id");
			mv.addObject("f-pw");
			return mv;
		}else {
			mv.addObject("success");
			mv.addObject("id", "grape111");
			mv.addObject("pw", "ggg9999");
			mv.addObject("response/s-result");
			return mv;
		}
	}
	*/
	/*
	//로그인 검증 요청 처리!
	@PostMapping("/s-login-check")
	public String loginCheck(Model model) {
		if(!model.getAttribute("id").equals("grape111")) {
			return "f-id";
		}else if(!model.getAttribute("pw").equals("ggg9999")) {
			return "f-pw";
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("id", model.getAttribute("id"));
		mv.addObject("pw", model.getAttribute("pw"));
		mv.addObject("/respose/s-result");
//		mv.addObject("successsuccess");
		return mv + "success";
	}
	*/
	
	//선생님 버전
	@PostMapping("/s-login-check")
	public String loginCheck(String id, String pw, Model model) {
		
		String result;
		
		if(id.equals("grape111")) {
			if(pw.equals("ggg9999")) {
				result = "success";
			}else {
				result = "f-pw";
			}
		}else {
			result = "f-id";
		}
		model.addAttribute("result", result);
		return "response/s-result";
	}
	
}




















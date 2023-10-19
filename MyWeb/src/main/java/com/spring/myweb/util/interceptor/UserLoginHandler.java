package com.spring.myweb.util.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//인터셉터로 사용할 클래스는 HandlerInterceptor라는 인터페이스를 구현합니다!
public class UserLoginHandler implements HandlerInterceptor{
	
	//preHandle은 컨트롤러로 요청이 들어가기 전 처리해야 할 로직을 작성.
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("나는 preHandle 이뵤~");
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	//postHandle은 컨트롤러를 나갈 때 공통 처리해야 할 내용을 작성.
	// /userLogin이라는 요청이 컨트롤러에서 마루리 된 후, viewResolver로 전달이 되기 전
	// 로그인 성공 or 실패 여부에 따라 처리할 로직을 작성할 계획입니다.
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("로그인 인터셉터가 동작했습니다~");
		System.out.println("요청 방식: " + request.getMethod());
		
		if(request.getMethod().equals("POST")) {//요청 방식이 POST라면 = 로그인 요청이었다면
			ModelMap map = modelAndView.getModelMap(); //여기까지가 우리가 매번 사용하던 model임
			//String dbPw = (String) map.get("dbPw");
			//String inputPw = (String) map.get("inputPw"); 필요 없어짐
			String result = (String) map.get("result");
			//result에는 id값 or null이 온다.
			
			
			if(result != null) { //result = id
				//로그인 성공
				//로그인 성공한 회원에게는 세션 데이터를 생성해서 로그인 유지를 하게 해 줌. -> 로그인 성공 뱃지를 주는 거임!
				HttpSession session = request.getSession();
				
				//여기서 뱃지의 내용을 좀 적당한 고유의 값인 userId값을 주고 싶은데
				//문제는 컨트롤러에서 model에 userId값을 넣어주지 않았음 -> 그럼 컨트롤러가서 값 넣어주면 되잖아.
				//그럼 model에 값이 3개나 담아야 함 -> 별루임 + preHandler 매개변수 이미 많음
				//해결: 서비스계층에서 if문으로 비밀번호 일치검사를 해서 일치: 아이디값 리턴, 불일치: 리턴 null
				session.setAttribute("login", result);
				//로그인 성공 했으니 메인페이지로 이동
				response.sendRedirect(request.getContextPath() + "/");
				
			}else { //result = null
				//로그인 실패
				modelAndView.addObject("msg", "loginFail");//그리고 view page를 바꾸지 않을 것임
			}
		}
	}
	
	//completion은 많이 사용하진 않음.
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("나는 afterCompletion 이뵤~");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
}

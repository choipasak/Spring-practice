package com.spring.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.spring.basic.model.UserVO;

//자동으로 스프링 컨테이너에 해당 클래스의 빈을 등록하는 아노테이션
//빈을 등록해 놔야(객체가 생성되어 있어야) HandlerMapping이 이 클래서의 객체를 검색할 수 있을 것이다.
//이 객체가 사용되려면 객체가 생성이 되어야 하잖아. 그럼 xml파일에 가서 bean어쩌구 다 해야해> -> 아님 -> @Controller
@Controller//("merong")//괄호 안의 이름이 객체등록이름이 된다! xml파일 안에서 지목할 일이 있다면 이렇게 작성해준다!
@RequestMapping("/request")//컨트롤러 자체의 공통된 URI 맵핑 -> /request로 시작하는 요청 다 여기서 받겠다!
public class RequestController {
	
	
	public RequestController() {
		System.out.println("RequestCon이 생성됨!");
	}
	
	@RequestMapping("/test")//()안처럼 요청이 들어오면 이 메서드를 불러줘! 라는 의미
	public String testCall() {
		System.out.println("/request/test 요청이 들어옴!");
		return "test";
	}
	
	@RequestMapping("/req")
	public String req() {
		System.out.println("/request/req 요청이 들어옴!");
		return "request/req-ex01";
		//return에 생성한 하위 폴더의 이름을 같이 경로처럼 붙여줘야함
	}
	
	//@RequestMapping(value = "/request/basic01", method = RequestMethod.GET)
	@GetMapping("/basic01")
	public String basicGet() {
		System.out.println("/basic01 요청이 들어옴!: GET");
		return "request/req-ex01";
	}
	
	//@RequestMapping(value = "/request/basic01", method = RequestMethod.POST)
	@PostMapping("/basic01")
	public String basicPost() {
		System.out.println("/basic01 요청이 들어옴!: POST");
		return "request/req-ex01";
	}
	//////////////////////////////////////////////////////////////////////////////////
	
	//컨트롤러 내의 메서드 타입을 void로 선언하면
	//요청이 들어온 URL 값을 뷰 리졸버에게 전달합니다.
	@GetMapping("/join")
	public void register() {//메서드가 void -> 맵핑된 url(/request/join)을 파일 경로로 삼겠다
		System.out.println("/request/join: GET");
	}
	//요청 URI 주소가 같더라도, 전송 방식에 따라 맵핑을 다르게 하기 때문에
	//같은 주소를 사용하는 것이 가능합니다. (GET -> 화면처리, POST -> 입력 값 처리)
	
	/*
	 # Spring에서 요청과 함께 전달된 데이터를 처리하는 방식
	 1. 전통적인 jsp/servlet 방식의 파라미터 읽기 처리 방법.
	 - HttpServletRequest 객체를 활용 (우리가 jsp에서 사용하던 방식)
	 -> 스프링에서는 잘 사용하지 않음
	@PostMapping("/join")
	public void register(HttpServletRequest request) {//오버로딩으로 메서드명 중복 허용시켜주기!
		System.out.println("/request/join: POST");
		System.out.println("ID: " + request.getParameter("userId"));
		System.out.println("PW: " + request.getParameter("userPw"));
		System.out.println("NAME: " + request.getParameter("userName"));
		System.out.println("HOBBY: " + Arrays.toString(request.getParameterValues("hobby")));
		
	}
	*/
	
	/*
	 2. @RequestParam 아노테이션을 이용한 요청 파라미터 처리.
	 @PostMapping("파라미터 변수명") 값을 받아서 처리 할 변수
	 파라미터 변수명과 값을 받을 변수명을 동일하게 작성하면 @PostMapping 생략 가능.
	@PostMapping("/join")
	public void register(//@RequestParam("name값")+ 받을 변수의 데이터타입 + 변수명
			String userId, //@RequestParam이 붙어있지 않더라도 변수명이 같으면 알아서 넣어줌.
			String userPw,
			@RequestParam(value = "hobby", required = false, defaultValue = "no hobby person") List<String> hobby //여러개의 데이터가 와도 데이터 타입을 List<>로 받기 가능
			//여기서 required = false해주는 이유: List<>로 받는 배열이기 때문.
			//변수의 타입이 일반형이면 그냥 빈문자값 ""이 오기 때문에 상관 없음.
			) {
		System.out.println("ID: " + userId);
		System.out.println("PW: " + userPw);
		System.out.println("hobby: " + hobby);
	}
	//@RequestParam: 무조건 값이 들어와야 함. -> 값이 없다면 error
	*/
	
	/*
	 3. 커맨드 객체를 활용한 파라미터 처리
	 - 파라미터 데이터와 연동되는 VO 클래스가 필요합니다.
	 -> 더 쉽게 입력값을 처리할 수 있음
	 - VO 클래스의 필드는 파라미터 변수명과 동일하게 작성합니다. (setter 메서드를 호출)
	 
	 # 커맨드 객체: 사용자의 입력을 담기 위해 설계되고, 특정 검증 로직이나 비지니스 로직을
	 수행할 수 있음. (VO보다는 역할이 좀 더 많고, 특정 목적을 가진 객체
	 - 파라미터 값을 받아오기 위해 VO객체를 사용하는 것을 커맨드 객체,,,라고도,,함,, 둘이 다른거임
	 - 요청과 함께 데이터가 넘어올 시에, 사용하는 것이 커맨드 객체/
	 => 커맨드 객체를 이용해서 파라미터를 처리할 때 VO를 많이 사용한다!
	 */
	@PostMapping("/join")
	public void register(UserVO vo) {
		System.out.println(vo);
		//VO의 은닉변수명과 파라미터 변수명이 같기 때문에 setter변수명이 같다는 것을 보고 자동으로 가져와서 값을 전달해 주는 것임.
		//롬복으로 @ToString을 작성해줬기 때문에 모든 파라미터 값을 알아서 출력해준다.
	}
	//근데 post방식에 한글을 보내서 한글이 깨짐 -> encoding 필요 -> 클라가 디스패쳐서블릿에 요청할 때! 필터(인코딩)를 껴준다
	
	
	
}

	
































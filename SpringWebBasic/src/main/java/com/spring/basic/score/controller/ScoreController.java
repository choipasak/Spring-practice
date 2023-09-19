package com.spring.basic.score.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.basic.score.dto.ScoreRequestDTO;
import com.spring.basic.score.service.ScoreService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/score")
@RequiredArgsConstructor //: final 필드가 존재한다면 그것을 초기화 해 주는 생성자./ 요구된 값을 매개 값으로 가지는 생성자
public class ScoreController {
	
	/*
	 1. 필요한 객체의 변수를 final로 선언
	 2. 각각 @Autowired를 다는 것은 좋지 않음.
	 3. 그래서 상수로 만든 다음 롬복으로 한꺼번에 @RequiredArgsConstructor를 사용해서 빈 등록된 
	 객체를 자동으로 변수에 주입해준다.
	 */
	private final ScoreService service;//서비스 계층 변수만 등록.
	//빈 등록이 된 ScoreService서비스 변수에다가 값을 주입해 달라고 하면 된다.-> @Autowired or @Qualifier
	//@Autowired -> 생성자에 붙이는 것을 제일 권장한다 했었음.
	//그러지말고 그냥 상수화 시켜버리기 -> 꼭 초기화가 필요함 -> @RequiredArgsConstructor -> 이제야 @Autowired
	
	//만약에 클래스의 생성자가 단 1개라면
	//자동으로 @Autowired를 작성해 줌.
	
	
	//1. 성적 등록 화면 띄우기 + 
	@GetMapping("/list")
	public String list() {
		return "score/score-list";//score 폴더에 있는 score-list로 갈거임.
	}

	//2. 성적 정보 등록 처리 요청.
	@PostMapping("/register")
	public String register(ScoreRequestDTO dto) {
		//커맨드객체인 데이터를 운반해주는 객체인 dto를 매개변수로 받겠습니다!
		//단순 입력 데이터 읽기
		System.out.println("/score/register: POST! - " + dto);
		
		//받은 dto를 서비스한테 보내서 일 시켜야지!(서비스에게 의존적인 컨트롤러)
		service.insertScore(dto);
		
		
		return null;
	}
}

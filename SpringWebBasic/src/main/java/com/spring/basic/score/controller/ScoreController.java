package com.spring.basic.score.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.basic.score.dto.ScoreListResponseDTO;
import com.spring.basic.score.dto.ScoreRequestDTO;
import com.spring.basic.score.entity.Score;
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
	
	/*
	@Autowired
	public ScoreController(ScoreService scoreService) {
		service = scoreService;
	} => 이렇게 작성해 줘야 하는 것을 @RequiredArgsConstructor 이거 하나로 해결!
	*/
	
	//1. 성적 등록 화면 띄우기 + 정보 목록 조회
	@GetMapping("/list")
	public String list(Model model) {//모델에 담아야하니까 매개변수는 Model
		
		//목록을 달라고 함 -> 받은 목록을 model에 담아서 응답
		List<ScoreListResponseDTO> dtoList = service.getList();
		
		//요청한 정제된 정보를 받았으니 model에 담아서 보낸다!
		model.addAttribute("sList", dtoList);
		
		return "score/score-list";//score 폴더에 있는 score-list.jsp로 갈거임.
	}

	//2. 성적 정보 등록 처리 요청.
	@PostMapping("/register")
	public String register(ScoreRequestDTO dto) {
		//커맨드객체인 데이터를 운반해주는 객체인 dto를 매개변수로 받겠습니다!
		//단순 입력 데이터 읽기
		System.out.println("/score/register: POST! - " + dto);
		
		//받은 dto를 서비스한테 보내서 일 시켜야지!(서비스에게 의존적인 컨트롤러)
		service.insertScore(dto);//서비스 호출
		
		//점수 등록이 되면 목록으로 가는게 좋지 않을까? -> /list 이 호출되어야 함 -> redirect가 필요함
		/*
		 등록 요청이 완료되었다면, 목록을 불러오는 로직을 여기다 작성하는 것이 아닌,
		 갱신된 목록을 불러오는 요청이 다시금 들어올 수 있도록 유도를 하자 -> sendRedirect()
		 
		 "redirect:[URL]" 을 작성하면 내가 지정한 URL로 자동 재요청이 일어나면서
		 미리 준비해 둔 로직을 수행할 수 있다.
		 점수 등록 완료 -> 목록을 달라는 요청으로 유도 -> 목록 응답.
		 */
		return "redirect:/score/list";
	}
	
	//3. 성적 정보를 상세 조회하는 요청
	@GetMapping("/detail")
	public String detail(int stuNum, Model model) {//매개변수에 @RequestParam이 생략된거 잊지말기!
									  //(어떻게가능: score의 변수이름과 매개변수 이름을 같게 해줬기 때문에!)
		System.out.println("/score/detail: GET!");
		//서비스에게 요청
		//Score score = service.retrieve(stuNum);
		//model.addAttribute("s", score);
		retrieve(stuNum, model);
		return "/score/score-detail";
	}
	
	//4. 성적 정보 삭제 요청(선택한 학생의 정보 삭제하는 요청)
	@GetMapping("/remove")
	public String remove(int stuNum) {
		
		System.out.println("/score/remove: GET!");
		service.delete(stuNum);
		
		return "redirect:/score/list";
		
	}
	
	//5. 수정 페이지(화면) 열어주기
	@GetMapping("/modify")
	public String modify(int stuNum, Model model) {
		retrieve(stuNum, model);
		return "score/score-modify";
	}
	
	//detail과 modify의 retrieve의 호출과 생성의 중복을 줄이기 위해서 메서드 생성!
	private void retrieve(int stuNum, Model model) {
		Score score = service.retrieve(stuNum);// -> 학번에 맞는 score객체를 줌
		model.addAttribute("s", score);
	}
	
	/*
	 * 내가 한 post-modify 버전
	@PostMapping("/modify")
	public String completemodify(int stuNum, ScoreRequestDTO dto, Model model) {
		
		//수정한 값을 새롭게 저장해 dto
		
		//서비스한테 수정한 값을 DB에 저장해달라고해!
		//ScoreService redto = service.insertScore(dto);
		
		//model.addAttribute("reRig", redto);
		
		return "redirect:/score/detail";
	}
	*/
	
	//선생님의 post-modify
	@PostMapping("/modify")
	public String modify(int stuNum, ScoreRequestDTO dto){
		System.out.println("/score/modify: POST!");
		
		service.modify(stuNum, dto);
		
		return "redirect:/score/detail?stuNum=" + stuNum;// /detail은 학번이 필요하다고 했기 때문에 쿼리스트링으로 붙여서 보낸다.
	}
	
	
}






























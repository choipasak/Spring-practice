package com.spring.myweb.freeboard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.spring.myweb.freeboard.dto.FreeContentResponseDTO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"
		,"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
// ㄴ> 다른 애들까지 사용해야해서 2개의 설정 다 가지고 왔음.
@WebAppConfiguration // 사전적 정의: SpringFramework에서 어플리케이션 컨텍스트의 웹 버전을 생성하는 데 사용하는 아노테이션
public class FreeBoardControllerTest {

	/*
	 # WebApplicationContext을 사용하는 이유
    - 테스트 환경에서 가상의 MVC를 구현하는 DispatcherServlet을 사용하기 위한 객체 자동 주입 -> 지금 Juint으로 test만 하면서 서버를 실제론 안돌리고 있어서
    - WebApplicationContext는 Spring에서 제공되는 servlet들을 사용할 수 있도록
    정보를 저장하는 객체입니다.
	 */
	@Autowired // 주입해조 -> WebApplicationContext타입의 객체를
	//등록된 모든 컨트롤러를 다 사용할 수 있는 데이터 타입의 변수
	private  WebApplicationContext ctx; // 아노테이션을 위한 변수
	/*
	 앞으로 컨트롤러가 굉장이 많이 생기면서 빈 등록이 되는데
	 그걸 전부 다 끌어서 쓰겠다.
	 하면 이렇게 작성해주는 것이고
	 아니다. 나는 그냥 하나의 컨트롤러만 사용하고 테스트 하겠다
	 -> private FreeBoardController controller; + @Autowired 작성 후 원하는 컨트롤러 타입과 변수를 선언한다.
	 그리고 this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	 => 하나만 쓸꺼야: 직접 사용할 컨트롤러의 타입과 standaloneSetup을 작성해준다.
	 */

	//mockMvc는 웹 어플리케이션을 서버에 배포하지 않아도 스프링 MVC 동작을
	//구현할 수 있는 가장의 구조를 만들어 줍니다.
	private MockMvc mockMvc;

	@BeforeEach // 가 붙은 메서드는 테스트 시작 시, 다른 메서드 실행 전에 항상 먼저 구동되는(선행되는) 기능을 한다.
	public void setup() { // 무조건 먼저 선행된다. -> @BeforeEach

		//가상 구조 세팅
		//스프링 컨테이너에 등록된 모든 빈과 의존성 주입까지 로드해서 사용이 가능.
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();

		/*
		 컨트롤러는
		 빈 등록 된거 다 끌어와야함
		 가상의 mvc구조를 구현해주고 + 요청에 맞게 응답도 보내야하고 여러가지 일을 해서 이렇게 필요한 객체들이 많다!
		 */
	}

	//test 로직부터 짜보기.(아직 실제 controller는 작성하지 않았음)
	@Test
	@DisplayName("/freeboard/freeList 요청 처리 과정 테스트")
	void testList() throws Exception { //지금 서버도 가동 안되어 있고, 화면단도 없는 상태
		//mock: 모조의 = 가짜의
		//MockMvcRequestBuilders: 가상의 환경에서 가상의 요청을 만들어주는 객체, 사용자처럼 가짜로 요청을 보내준다!
		ModelAndView mv = mockMvc.perform(MockMvcRequestBuilders.get("/freeboard/freeList"))// 가상의 요청을 보냄!(GET)
				.andReturn() // 요청의 결과를 받음!
				.getModelAndView(); // 요청 결과에서 ModelAndView 객체를 얻음.
		// 빨간줄이 뜨는데 try-catch필요 -> test기 때문에 throws

		//결과 까보기
		//컨트롤러에서 Model 객체에 담은 데이터를 확인
		System.out.println("Model 내에 저장한 데이터: " + mv.getModelMap());
		//컨트롤러에서 응답 페이지로 지정한 문자열을 확인.
		System.out.println("응답 처리를 위해 사용할 페이지: " + mv.getViewName()); // .jsp는 안붙음 -> viewResolver가 하는일이니까
		//결과: boardList의 내용과 응답 페이지 경로가 뜬다! 
	}
	
	@Test
	@DisplayName("게시글 등록 완료 후 목록 재요청이 일어날 것이다.")
	void testInsert() throws Exception {
		String viewName = 
		mockMvc.perform(MockMvcRequestBuilders.post("/freeboard/freeRegist")
											  .param("title", "테스트 새 글 제목")
											  .param("content", "테스트 새 글 내용")
											  .param("writer", "user01")
											  // .param()은 요청이 들어오면 요청과 함께 전달할 데이터를 묻히고 싶을 때 사용하는 메서드
				).andReturn().getModelAndView().getViewName();
		
		assertEquals(viewName, "redirect:/freeboard/freeList");//실제 컨트롤러를 작성해야 여기도 test가 가능해짐
		
	}
	
	@Test
	@DisplayName("3번 글 상세보기 요청을 넣으면, 컨트롤러는 DB에서 가지고 온 글 객체를 model에 담아서 freeDetail.jsp로 이동시킬 것이다.")
	void testContent() throws Exception {
		// /freeboard/content -> GET!
		// bno, title, writer, content, updateDate == null? regDate, updateDate 들은 수정됨
		
		
		ModelAndView mv = mockMvc.perform(
					MockMvcRequestBuilders.get("/freeboard/content")
												  .param("bno", 1+"")//이렇게 해주면 Spring이 자동으로 String으로 바꿔줌
					).andReturn().getModelAndView();
		
		//model에 담는 정보가 저장되는 .getModelMap
		//.getModelMap의 메서드인 .get("내가 model객체에 담을 때 지은 이름")은 model객체에 담은 정보를 빼온다!
		FreeContentResponseDTO dto = (FreeContentResponseDTO) mv.getModelMap().get("gocontent");
		//.get()의 리턴값은 object임 object는 부모니까 여기서 3번글인ㄴ거를 getBno를 해주려면 FreeContentResponseDTO 타입으로 내려줘야함. 그래야 getBno메서드를 사용할 수 있으니까.
		// 그래서 형변환을 해주고 변수 dto로 선언 해 준 다음 번호를 뽑아서 그 번호가 3번이 맞냐! 로 assert를 해줄 수 있는 것이다.

		//검증
		assertEquals(mv.getViewName(), "freeboard/freeDetail");
		System.out.println(dto);
		assertEquals(dto.getBno(), 1);
	}
	
	@Test
    @DisplayName("3번글의 제목과 내용을 수정하는 요청을 post방식으로 전송하면 수정이 진행되고, "
            + "수정된 글의 상세보기 페이지로 응답해야 한다.")
    // /freeboard/modify -> post
	// 작성자는 수정하지 않는다.
    void testModify() throws Exception {
		
		String bno = "3";
		
		String viewName = mockMvc.perform(MockMvcRequestBuilders.post("/freeboard/modify")
    			.param("bno", bno)
    			.param("title", "일기장")
    			.param("content", "아샷추마싯다")
    			)
				.andReturn()
				.getModelAndView()
				.getViewName();
		
		
		assertEquals(viewName, "redirect:/freeboard/content?bno=" + bno);
        
    }
    
    @Test
    @DisplayName("지울개.3번글 -> 3번 글을 삭제하면 목록 재요청이 발생할 것이다.")
    // /freeboard/delete -> post
    void testDelete() throws Exception {
    	
    	assertEquals("redirect:/freeboard/freeList", 
    			mockMvc.perform(MockMvcRequestBuilders.post("/freeboard/delete").param("bno", 3+""))
				.andReturn()
				.getModelAndView()
				.getViewName()
				);
    	
        
    }

}


















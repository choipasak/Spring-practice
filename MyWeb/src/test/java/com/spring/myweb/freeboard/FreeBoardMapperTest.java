package com.spring.myweb.freeboard;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.spring.myweb.freeboard.entity.FreeBoard;
import com.spring.myweb.freeboard.mapper.IFreeBoardMapper;

//import static: Assertions클래스 안에 있는 클래스들은 Assertions라는 이름 안붙이고 사용할 수 있음
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) // 테스트 환경을 만들어 주는 Junit5 객체를 로딩 해 주는 롬복
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
// ㄴ> 부르는 이유: 여기에 Mybatis설정이 있음. DB설정을 가지고 오겠다!
public class FreeBoardMapperTest {
	
	@Autowired
	private IFreeBoardMapper mapper;
	
	
	//단위 테스트 (unit test) - 가장 작은 단위의 테스트 ( 기능별 테스트 -> 메서드 별 테스트)
	//테스트 시나리오
	// - 단언(Assertion) 기법
	
	@Test
	@DisplayName("Mapper 계층의 register를 호출하면서"
			+ "Freeboard를 전달하면 데이터가 INSERT 될 것이다.")// test하고자 하는 목표를 ()안에 작성
	void registTest() {
		//given - when then 패턴을 따릅니다. (권장 사항)
		//없으면 각각 모두 생략 가능
		
		//given: 테스트를 위해 주어질 데이터 세팅 (parameter) - 생략(parameter값이 없음, 자체적으로 값을 준거임)
		/*
		for(int i=1; i<=10; i++) {
			//when: 테스트 실제 상황 세팅
			mapper.regist(FreeBoard.builder()
					.title("테스트 제목" + i)
					.writer("abc1234")
					.content("테스트 내용입니다." + i)
					.build());
		}*/
		//then: 테스트 결과를 확인하는 곳. -> SELECT를 해야하는 조회문이 지금 없으니 결과 확인 못함
		
		mapper.regist(FreeBoard.builder()
				.title("테스트 제목")
				.writer("메롱메롱")
				.content("테스트 중이니 조용조용.")
				.build());
	}
	
	//when
	@Test
	@DisplayName("조회 시 전체 글 목록이 올 것이고, 조회 된 글의 개수는 10개일 것이다.")
	void getListTest() {
		List<FreeBoard> list = mapper.getList();
		for(FreeBoard f : list) {
			System.out.println(f);
		}
		
		//then
		assertEquals(list.size(), 11);
		
	}
	
	@Test
	@DisplayName("글 번호가 11번인 글을 조회하면 글쓴이는 반드시 '메롱메롱'일 것이고,"
			+ "글 내용은 '테스트 중이니 조용조용.'일 것이다.")
	void getContentTest() {
		
		//given
		int bno = 11;
		
		//when
		FreeBoard board = mapper.getContent(bno);
		
		//then
		assertTrue(board.getWriter().equals("메롱메롱")); //false 버전도 있음
		assertEquals(board.getContent(), "테스트 중이니 조용조용."); 
	}
	
	//글 번호가 1번인 글의 제목과 내용을 수정 후 다시 조회했을 때
	//수정한 제목과 내용으로 바뀌었음을 단언하세요.
	@Test
	@DisplayName("글 번호가 1번인 글의 제목과 내용을 수정 후"
			+ "다시 조회했을 때 수정한 제목과 내용으로 바뀌었다.")
	void updateTest() {
		
		//나.ver
		//given
		int bno = 1;
		String title = "수정1";
		String writer = "수정자";
		String content = "수정뵤";
		
		
		//when
		FreeBoard board = mapper.getContent(bno);
		mapper.update(FreeBoard.builder()
				.bno(bno)
				.title(title)
				.writer(writer)
				.content(content)
				.build());
		
		//then
		assertEquals(board.getTitle(), title);
		assertEquals(board.getWriter(), writer);
		/*
		//선생님.ver
		//given
		FreeBoard b = FreeBoard.builder()
				.bno(bno)
				.title(title)
				.writer(writer)
				.content(content)
				.build();
		//when
		mapper.update(b);
		//then
		Freeboard bb = mapper.getContent(board.getBno()); // DB에서 조회 결과 얻어오기.
		assertEquals(board.getTitle(), mapper.getContent(board.getBno()).getTitle());
		*/
	}
	
	
	
	//글 번호가 2번인 글을 삭제한 후에 전체 목록을 조회했을 때
	//글의 개수는 11개일 것이고
	//2번 글을 조회했을 때 null이 리턴됨을 단언하세요 -> assertNull(객체)
	
	@Test
	@DisplayName("2번 글 삭제하면 글 개수는 10개고"
			+ "2번 글을 조회하면 null값을 리턴된다.")
	void deleteTest() {
		
		//given
		int bno = 2;
		
		//when
		//List<FreeBoard> list = mapper.getList();
		mapper.delete(bno);
		
		//then
		assertEquals(mapper.getList().size(), 11);
		assertNull(mapper.getContent(bno));
		
		//선생님.ver
		//given
		int bno1 = 2;
		
		//when
		mapper.delete(bno1);
		
		//then
		assertEquals(mapper.getList().size(), 11);
		assertNull(mapper.getContent(bno1));
	}

}



























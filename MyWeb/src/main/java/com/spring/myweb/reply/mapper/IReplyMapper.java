package com.spring.myweb.reply.mapper;

import java.util.List;
import java.util.Map;

import com.spring.myweb.reply.entity.Reply;

//원래 테스트 해야하는데 시간 상 생략
public interface IReplyMapper {

	//댓글 등록
	void replyRegist(Reply reply);
	
	//댓글 목록 요청 -> 몇번 째 게시물의 댓글인지
	/*
	 - MyBatis로 DB연동을 진행할 때, 파라미터 값이 2개 이상일 때 그냥 보내시면
	 	에러가 발생하기 때문에 조치가 필요합니다.
	 	
	 1. @Param을 이용해서 이름을 붙여주는 방법. (mapper.xml에서 해당 값을 지목할 수 있는 이름 붙이기)
		-> List<Reply> getList(@Param("boardNo") int bno,@Param("paging") Page page);
	 
	 2. Map으로 포장해서 보내는 방법. -> 일회성으로 사용할 것 같으면 Map으로!
		-> List<Reply> getList(Map<String, Object> data); // Map으로 포장해서 보내라는 뜻, key-> mapper에서 사용할 이름. 이 되는 것임.
	 
	 3. 클래스를 따로 생성+디자인 해서 객체 하나만 매개 값으로 보내는 방법
	 @builder를 사용해서 값을 하나로 만들어서 리턴 해 주면 된다. (원래 계속 사용하던 방식) -> 자주 사용될 것 같으면 생성!
	 
	 중 하나를 상황에 맞게 제일 효율적인 방법을 적절하게 선택하시면 됩니다.
	 */
	List<Reply> getList(Map<String, Object> data);
			
	//댓글 개수(페이징, PageCreator(페이징 알고리즘객체 파일)는 사용하지 않습니다. 비동기 방식에 어울리는 페이징 사용 예정) -> 댓글 밑의 more버튼 클릭 -> +5개 댓글
	//비동기 페이징은 js에서 해도 되고, 백에서 해도 된다.
	int getTotal(int bno);
	
	//비밀번호 확인 - 매개 값: 댓글 번호
	String pwCheck(int rno);
	
	//댓글 수정
	void update(Reply reply);
	
	//댓글 삭제
	void delete(int rno);
	
}

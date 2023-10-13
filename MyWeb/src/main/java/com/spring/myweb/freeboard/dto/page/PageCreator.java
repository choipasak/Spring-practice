package com.spring.myweb.freeboard.dto.page;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class PageCreator {

	//한 화면에 배치할 버튼의 개수
	private static final int BUTTON_NUM = 5;

	//화면 렌더링 시 페이지의 시작값과 끝값.
	private int begin, end;

	//이전, 다음 버튼 활성화 여부
	private boolean prev, next;

	//현재 요청 페이지 정보
	private Page page;

	//총 게시물 수
	private int articleTotalCount;

	//외부로부터 페이징 알고리즘을 수행하기 위해 외부로부터 필요한 데이터(= 요청 페이지)를 전달받음.
	public PageCreator(Page page, int articleTotalCount) {
		this.page = page;
		this.articleTotalCount = articleTotalCount;
		calcDataOfPage(); //전달 완료 후 알고리즘 수행.
	}
	/*
	 page.amount는 한 페이지에 보여질 게시물의 갯수를 의미하는거고
	 articleTotalCount는 현재 DB의 총 게시물 갯수이니까
	 */
	private void calcDataOfPage() {

		//끝 페이지 계산
		end = (int) (Math.ceil (page.getPageNo() / (double) BUTTON_NUM) * BUTTON_NUM);

		//시작 페이지 계산
		this.begin = this.end - BUTTON_NUM + 1;
		
		//이전 버튼
		this.prev = begin > 1; //: 만약 begin = 1 -> prev:false, begin > 1 -> prev:true 라는 삼항연산식과 같은 결과가 나옴
		
		//다음 버튼
		this.next = articleTotalCount <= (end * page.getAmount()) ? false: true;
		
		//보정
		if(!this.next) {
			this.end = (int) Math.ceil(articleTotalCount / (double) page.getAmount());
		}
		

	}

}








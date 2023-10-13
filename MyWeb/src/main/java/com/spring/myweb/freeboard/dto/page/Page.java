package com.spring.myweb.freeboard.dto.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@AllArgsConstructor
@Builder
public class Page {
	
	private int pageNo; // 클라이언트가 보낸 페이지 번호
	private int amount; // 한 페이지에 보여질 게시물 수
	
	//검색 요청에 필요한 필드를 추가
	private String keyword;
	private String condition;

	public Page() {
		// 매개 값이 하나도 들어오지 않는다면 = 사용자가 페이지에 처음 들어왔을 때
		this.pageNo = 1;
		this.amount = 10;
		// 위의 값을 기본 값으로 설정 하겠다.
	}
	
	//롬복을 이용해서 setter를 작성 했지만 커스텀이 필요해서 setter작성
	public void setPageNo(int pageNo) {
		//지식이 있는 사람이 이상하게 페이지 번호를 작성하는 것을 방지
		//Integer.MAX_VALUE: int의 최대값의 값을 가진 메서드
		if(pageNo < 1 || pageNo > Integer.MAX_VALUE) {
            this.pageNo = 1;
            return;
        }
        this.pageNo = pageNo;
	}
	
	public void setAmount(int amount) {
		//하나의 페이지에 보고싶은 게시글의 개수를 막 1000개씩 보고싶다 -> 막아야함
		//예) 한 페이지에 10개 단위로 10개, 20개, 30개씩 까지만 보여주고 싶음.
		if(amount < 10 || amount > 30 || (amount % 10 != 0)) {
            this.amount = 10;
            return;
        }
        this.amount = amount;
	}
	
	public int getPageStart() {
		/*
		 pageNo: 1-> return 0
		 pageNo: 2-> return 10
		 pageNo: 3-> return 20
		 pageNo: 4-> return 30
		 */
		return (pageNo - 1) * amount;
	}
	
	public int getPageEnd() {
		return pageNo * amount;
	}
}

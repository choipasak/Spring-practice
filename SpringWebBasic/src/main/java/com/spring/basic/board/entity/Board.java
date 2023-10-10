package com.spring.basic.board.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
// Entity 클래스
public class Board {
	
	private int boardNo;
	private String writer;
	private String title;
	private String content;
	private LocalDateTime regDate;
	
	// updateArticle()는 매개 값으로 Board객체를 받음
		// modify()는 매개 값이 dto임
		// dto -> board 값을 넣어줘야 함.
		// 근데 넣을 값의 개수가 다를 때마다 이렇게 적어줄 순 없음
		// 결론: builder pattern을 이용
		// (롬복에서 @Builder 제공)
		
		/*
		 빌더 패턴 (Builder Pattern)
		 - 객체를 생성하는 방법을 정의하는 내부 클래스.
		 - 기존에는 생성자 혹은 setter 메서드를 통해 객체의 필드를 직접 초기화를 했다면,
		   Builder 클래스를 디자인하고 활용하여 원하는 필드만 빠르게 초기화 할 수 있고,
		   코드 가독성도 뛰어나 질 수 있다.
		 - 롬복으로 생성이 가능한다.
		 */
		
		//중첩 클래스(내부 클래스) 선언 - 중첩클래스에만 static선언 가능(static: 변수, 메서드만 가능)
		// 1. 중첩 static 클래스를 이용하여 기존 객체와 동일한 모양의 클래스를 하나 더 구축합니다.(= 똑같은 모양으로 만듬)
		/*
		public static class BoardBuilder{
			
			// 기존 객체와 동일한 필드를 갖습니다.
			private int boardNo;
			private String writer;
			private String title;
			private String content;
			private LocalDateTime regDate;
			
			// 2. 내부 클래스의 생성자를 private으로 제한해서
			// Board클래스 밖에서는 객체 생성 호출할 수 없도록 지정.
			// => Board만 부를 수 있게 만들겠다.
			private BoardBuilder() {}
			
			
			// 3. 필드 별 setter만 구축 합니다.
			// return type은 BoardBuilder 객체를 리턴하게끔 작성합니다.
			public BoardBuilder boardNo(int boardNo) {
				this.boardNo = boardNo;
				return this;
			}
			public BoardBuilder writer(String writer) {
				this.writer = writer;
				return this;
			}
			public BoardBuilder title(String title) {
				this.title = title;
				return this;
			}
			public BoardBuilder content(String content) {
				this.content = content;
				return this;
			}
			public BoardBuilder regDate(LocalDateTime regDate) {
				this.regDate = regDate;
				return this;
			}
			
			// 4. 빌더 완료 메서드를 선언합니다.
			public Board build() {
				return new Board(boardNo, writer, title, content, regDate);
			}
			
		}//내부클래스 끝 -> BoardBuilder 끝!
		
		
		// 5. 빌더 호출 메서드 (Board가 BoardBuilder를 호출하는 메서드)
		public static BoardBuilder builder() {
			return new BoardBuilder();
		}
		 */
	
}

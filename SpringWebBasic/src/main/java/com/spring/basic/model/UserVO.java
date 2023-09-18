package com.spring.basic.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//자바 빈 규약
@Getter @Setter
@ToString
@EqualsAndHashCode //-> 하지 않는다면: Hashset이 이상하게 작동함 (그냥 다른 소리)
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

	private String userId;
	private String userPw;
	private String userName;
	private List<String> hobby;
	// private변수명을 파라미터 변수명과 똑같이 맞춰줘야 한다.
	
}

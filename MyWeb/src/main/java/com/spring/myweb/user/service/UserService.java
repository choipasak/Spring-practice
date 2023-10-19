package com.spring.myweb.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.myweb.user.dto.UserInfoResponseDTO;
import com.spring.myweb.user.dto.UserJoinRequestDTO;
import com.spring.myweb.user.entity.User;
import com.spring.myweb.user.mapper.IUserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final IUserMapper mapper;
	
	//입력받은 비밀번호를 암호화 시켜 줄 객체!
	private final BCryptPasswordEncoder encoder;

	public int idCheck(String account) {
		return mapper.idCheck(account);
		
	}

	public void join(UserJoinRequestDTO dto) {
		
		//회원 비밀번호를 암호화 인코딩해주기
		//암호화 전 비번 확인
		System.out.println("암호화 하기 전 비번: " + dto.getUserPw());
		//비번 암호화해서 dto에 다시 저장하기
		String securePw = encoder.encode(dto.getUserPw());
		System.out.println("암호화 후 비번: " + securePw);
		
		
		//dto -> entity 변환
		User user = User.builder()
				.userId(dto.getUserId())
				.userPw(securePw)
				.userName(dto.getUserName())
				.userPhone1(dto.getUserPhone1())
				.userPhone2(dto.getUserPhone2())
				.userEmail1(dto.getUserEmail1())
				.userEmail2(dto.getUserEmail2())
				.addrBasic(dto.getAddrBasic())
				.addrDetail(dto.getAddrDetail())
				.addrZipNum(dto.getAddrZipNum())
				.build();
		
		mapper.join(user);
	}

	public String login(String userId, String userPw) {
		String dbPw = mapper.login(userId);
		if(dbPw != null) {//dbPw값이 조회가 됬다면
			//날것의 비밀번호와 암호화된 비밀번호의 일치 여부를 알려주는 matches()를 사용해서 비교!
			if(encoder.matches(userPw, dbPw)) { //encoder.matches()가 userPw를 암호화시켜서 dbPw와 일치하냐 비교해줌. -> 리턴:boolean
				return userId;
			}
		}
		return null;
	}
	
	
	public UserInfoResponseDTO getInfo(String id) {
		User user = mapper.getInfo(id);
		return UserInfoResponseDTO.toDTO(user);
	}
	
	

	
	
}

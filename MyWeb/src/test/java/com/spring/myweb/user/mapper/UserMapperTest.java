package com.spring.myweb.user.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.spring.myweb.user.entity.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class UserMapperTest {
	
	@Autowired
	private IUserMapper mapper;
	
	@Test
    @DisplayName("회원 가입을 진행했을 때 회원가입이 성공해야 한다.")
    void registTest() {
		
		mapper.join(User.builder()
				.userId("abc112")
				.userPw("aaa111!")
				.userName("춘식")
				.userPhone1("010-1111-2222")
				.userEmail1("abc112@gmail.com")
				.addrBasic("rowstreet12-2")
				.addrDetail("1st_floor")
				.build());
		
		assertFalse(false);
    }
    
    @Test
    @DisplayName("존재하는 회원 아이디를 조회했을 시 1이 리턴되어야 한다.")
    void checkIdTest() {
    	
    	//int idchktest = mapper.idCheck(UserRequestDTO.builder().userId("gogo123").build());
    	
    	String id = "gogo123";
    	
    	assertEquals(mapper.idCheck(id), 1);
        
    }
    
    @Test
    @DisplayName("존재하는 회원 아이디를 입력 했을 시 그 회원의 비밀번호가 리턴되어야 한다.")
    void loginTest() {
        String id = "gogo123";

        //String chkPw = mapper.login(UserRequestDTO.builder().userId("gogo123").build());
        		
        assertNotNull(mapper.login(id));
        assertEquals("gogo123", mapper.login(id));
        
    }
    
    @Test
    @DisplayName("존재하지 않는 회원의 아이디를 전달하면 null이 올 것이다.")
    void getInfoTest() {
    	
    	String id = "merong";
    	
    	assertNull(mapper.getInfo(id));
    	
    }
    
    @Test
    @DisplayName("id를 제외한 회원의 정보를 수정할 수 있어야 한다.")
    void updateTest() {
    	
    	User user = User.builder()
    			.userId("aaaAAAU3U")
    			.userPw("babo6v6")
    			.userName("바보바보")
    			.userEmail1("babo6v6")
    			.userEmail2("naver.com")
    			.addrBasic("서울 특별시")
    			.addrDetail("64-219")
    			.addrZipNum("05383")
    			.build();
    	mapper.updateUser(user);
    	
    	assertEquals(user.getUserEmail1(), mapper.getInfo("abc112").getUserEmail1());
        
    }
	
	
	

}

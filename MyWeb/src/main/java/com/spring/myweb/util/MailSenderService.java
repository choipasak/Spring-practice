package com.spring.myweb.util;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component //controller도 아니고 service도 아닌 걸 등록하고 싶을 때 @Component -> 빈 등록 아노테이션
@RequiredArgsConstructor
public class MailSenderService {
	//여기는 Mail에 대한 전반적인 것들을 담당할 객체
	
	//email-config에 등록한 빈 주입
	private final JavaMailSender mailSender;
	
	
	//이메일로 보낼 인증번호
	//난수 생성
	private int makeRandomNumber() {
		//난수의 범위: 111111 ~ 999999
		Random r = new Random();
		int checkNum = r.nextInt(888888) + 111111;
		
		System.out.println("인증 번호는: " + checkNum);
		
		return checkNum;
	}
	
	//가입할 회원에게 전송할 이메일 양식을 준비하자
	//밖에서 UserController가 얘를 불러야함 -> public -> 인증번호를 return
	public String joinEmail(String email) {
		
		// 일단 인증번호 생성
		int authNum = makeRandomNumber();
		
		//양식 준비
		String setFrom = "chlwjdgus822@naver.com"; //email-config에 설정한 발신용 이메일 주소 입력.
		String toMail = email; //수신받을 이메일(가입하고자 하는 사람의 이메일)
		String title = "MyWeb 회원 가입 인증 이메일 입니다."; //이메일의 제목
		String content = "홈페이지 가입을 신청해 주셔서 감사합니다."
				+ "<br><br>"
				+ "인증 번호는 <strong>" + authNum + "</strong> 입니다."
						+ "<br>"
						+ "해당 인증 번호를 인증번호 확인란에 기입해 주세요."; //이메일에 삽입할 내용
		
		//mailSend(setFrom, toMail, title, content);
		
		
		//문자열로 인증번호를 넘기겠음.
		return Integer.toString(authNum);
	}

	//얘가 이메일을 실제로 전송하는 메서드
	private void mailSend(String setFrom, String toMail, String title, String content) {
		
		try {
			
			MimeMessage message = mailSender.createMimeMessage();
			//1. 기타 설정들을 담당할 MimeMessageHelper 객체를 생성.
			//2. 생성자의 매개값으로는 MimeMessage 객체, bool, 문자 인코딩 설정
			//3. true 매개값을 전달하면 MultiPart 형식의 메세지 전달이 가능. (첨부 파일)
			//4. 값을 전달하지 않는다면 단순 텍스트만 사용.

			//helper객체를 생성
			MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			// true -> html 형식으로 전송, 값을 안주면 단순 텍스트로 전달.
			helper.setText(content, true); // helper를 통해 설정에 값 세팅
			
			//메일 전송!
			mailSender.send(message);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	
	
	

}




















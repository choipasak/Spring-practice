package com.spring.myweb.snsboard.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.spring.myweb.snsboard.dto.SnsBoardRequestDTO;
import com.spring.myweb.snsboard.dto.SnsBoardResponseDTO;
import com.spring.myweb.snsboard.service.SnsServiceBoard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/snsboard")
@RequiredArgsConstructor
@Slf4j
public class SnsBoardController {

	private final SnsServiceBoard service;

	@GetMapping("/snsList")
	public ModelAndView snsList() {
		ModelAndView mv = new ModelAndView();
		//mv.addAllObjects("name", "value");
		mv.setViewName("snsboard/snsList");
		return mv; //ModelAndView를 호출하면 비동기방식컨트롤러 안에서 동기방식인 원래대로 작동시킬 수 있다.
	};

	/*
	 정리: @RestController안에서 비동기 통신을 사용하지 않고 통신을 하고 싶다
	 	 -> 리턴 타입을 ModelAndView로 잡으면 된다.
	 */


	@PostMapping("/upload")
	public String upload(SnsBoardRequestDTO dto) {
		service.insert(dto);

		return "uploadSuccess";
	}

	@GetMapping("/{page}")
	public List<SnsBoardResponseDTO> getList(@PathVariable int page) { //()생략 가능: 변수명=매개변수명
		log.info("/snsboard/getList: GET!");
		return service.getList(page); // List를 리턴
	}

	/*
	    # 게시글의 이미지 파일 전송 요청
	    이 요청은 img 태그의 src 속성을 통해서 요청이 들어오고 있습니다.
	    snsList.jsp 페이지가 로딩되면서, 글 목록을 가져오고 있고, JS를 이용해서 화면을 꾸밀 때
	    img 태그의 src에 작성된 요청 url을 통해 자동으로 요청이 들어오게 됩니다.
	    요청을 받아주는 메서드를 선언하여 경로에 지정된 파일을 보낼 예정입니다.
	 */
	@GetMapping("/display/{fileLoca}/{fileName}")
	public ResponseEntity<?> getImage(@PathVariable String fileLoca,@PathVariable String fileName) {
		log.info("fileLoca | 파일경로 : " + fileLoca);
		log.info("fileName | 파일이름 : {}", fileName); //값을 여러 개 주고 싶다면 들어갈 자리에 {}작성 -> 값을 차례로 ,값 으로 주기!
		
		File file = new File("C:/test/upload/" + fileLoca + "/" + fileName);
		log.info("풀 파일경로: " + file.toString()); // 완성된 경로
		
		//응답에 대한 여러가지 정보를 전달할 수 있는 객체 ResponseEntity
		//응답 내용, 응답이 성공했는지에 대한 여부, 응답에 관련된 여러 설정들을 지원합니다.
		ResponseEntity<?> result = null;
		
		HttpHeaders headers = new HttpHeaders();//응답 헤더 객체생성.
		//응답에 관한 것들을 headers에 담을 수 있음!
		
		try {
			headers.add("Content-Type", Files.probeContentType(file.toPath()));
            //Files의 probeContentType: 매개값으로 전달받은 파일의 타입이 무엇인지를 문자열로 반환.
            //사용자에게 보여주고자 하는 데이터가 어떤 파일인지에 따라 응답 상태 코드를 다르게 리턴하고
            //컨텐트 타입을 제공해서 화면단에서 판단할 수 있게 처리할 수 있다.
			//원하는 정보를 더 담고 싶다면 똑같이 headers.add()해서 넣어주면 된다!
			//headers.add("value", "hello world!");
			
			//ResponseEntity 객체에 전달하고자 하는 파일을 byte[]로 변환해서 전달해야 한다: byte단위로 쪼개서 보내야 파일의 손상을 방지 가능
			//headers 내용도 같이 포함, 응답 상태 코드를 원하는 형태로 전달이 가능!
			//일단 위에서 선언한 file을 byte로 전환시켜줘야 한다.
			
			//생성자를 이용하여 ResponseEntity 생성하는 법.
			result = new ResponseEntity<>(
					FileCopyUtils.copyToByteArray(file),
					headers,
					HttpStatus.OK); // 실제 파일 경로를 바이트로 변환시켜서 전달
					// 저 .OK는 200을 말함 -> 그 200 코드 맞음
			
			
		} catch (IOException e) {
			e.printStackTrace();
			//static 메서드를 활용하여 ResponseEntity 객체를 생성하는 법
			return result = ResponseEntity.badRequest().body(e.getMessage()); //이게 400코드 말하는 메서드임.
		}
		//제네릭의 <>안에 ? : 생성자를 이용하여 ResponseEntit
		return result;
		
		//사실 REST통신 방식에서는 ResponseEntity를 사용하는 것을 권장한다!
	}
	
	//다운로드 요청!
	@GetMapping("/download/{fileLoca}/{fileName}")
	public ResponseEntity<?> download(@PathVariable String fileLoca, @PathVariable String fileName){
		
		File file = new File("C:/test/upload/" + fileLoca + "/" + fileName);
		System.out.println("파일경로: " + file);
		
		ResponseEntity<byte[]> result = null;
		
		//다운로드를 제공할 땐 headers가 꼭 필요 -> headers에 지정된 명칭으로 정보를 주기 때문!
		HttpHeaders header = new HttpHeaders();
        //응답하는 본문을 브라우저가 어떻게 표시해야 할 지 알려주는 헤더 정보를 추가합니다.
        //inline인 경우 웹 페이지 화면에 표시되고, attachment인 경우 다운로드를 제공합니다.
        
        //request객체의 getHeader("User-Agent") -> 단어를 뽑아서 확인
        //ie: Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko  
        //chrome: Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36
    
        //파일명한글처리(Chrome browser) 크롬
        //header.add("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") );
        //파일명한글처리(Edge) 엣지 
        //header.add("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        //파일명한글처리(Trident) IE
        //Header.add("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", " "));
		header.add("Content-Disposition", "attachment; filename=" + fileName);
		// 2번째 매개변수가 "inline"이면 그냥 보기만을 제공, "attach": 이미지 다운로드 제공
		try {
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	
	//글 상세보기 요청!
	@GetMapping("/content/{bno}")
	public SnsBoardResponseDTO content(@PathVariable int bno) {
		
		
		
		//내가 한 버전
		SnsBoardResponseDTO resdto = service.getContent(bno);
		//System.out.println("누른 사진 번호: " + resdto);
		
		//방법 2번 : responseEntity사용
		//return ResponseEntity.ok().body(service.getContent(bno)); -> 메서드 타입도 바꿔줘야함.
		
		return resdto;
	}
	
	//글 삭제하기 요청
	@DeleteMapping("/{bno}")
	public ResponseEntity<?> delete(@PathVariable int bno, HttpSession session) {
		System.out.println("삭제버튼 글 번호: " + bno);
	
		
		//return service.delete(bno); -> ㄴㅐ가 한 것
		
		String id = (String) session.getAttribute("login"); // 로그인 하고 있는 사용자 뽑기.
		SnsBoardResponseDTO dto = service.getContent(bno);
		if(id == null || !id.equals(dto.getWriter())) { // 이 조건이 trun -> 권한X
			//return "noAuth";
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		service.delete(bno);
		//글 삭제 -> 이미지도 존재할 필요 X
		//이미지도 함께 삭제 해 주셔야 합니다.
		//File 객체 생성 -> 생성자에 지우고자 하는 파일의 경로 지정
		//메서드 delete() -> return type이 boolean. 삭제 성공시 true, 실패 시 false.
		//글번호를 이용! -> 상세보기
		File file = new File(dto.getUploadPath() + "/" + dto.getFileName());
		System.out.println("확인한다");
		System.out.println(dto.getUploadPath());
		System.out.println(dto.getFileLoca());
		System.out.println(dto.getFileName());
		return file.delete()
				? ResponseEntity.status(HttpStatus.OK).build()
				: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 리턴타입 boolean
	}
	
	
	//좋아요 버튼 클릭 처리
	@PostMapping("/like")
	public String likeConfirm(@RequestBody Map<String, String> params) {
		log.info("/like: POST, params : {}", params);
		
		return service.searchLike(params);
		
	}
	
	
	//회원이 글 목록으로 진입 시 좋아요 게시물 리스트 체크
	@GetMapping("/likeList/{userId}")
	public List<Integer> likeList(@PathVariable String userId) {
		log.info("/snsboard/likeList: GET, userId: {}", userId);
		return service.likeList(userId);
	}

}














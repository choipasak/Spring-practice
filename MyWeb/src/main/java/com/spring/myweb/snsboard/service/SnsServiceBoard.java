package com.spring.myweb.snsboard.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.spring.myweb.freeboard.dto.page.Page;
import com.spring.myweb.snsboard.dto.SnsBoardRequestDTO;
import com.spring.myweb.snsboard.dto.SnsBoardResponseDTO;
import com.spring.myweb.snsboard.entity.SnsBoard;
import com.spring.myweb.snsboard.mapper.ISnsBoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SnsServiceBoard {

	private final ISnsBoardMapper mapper;

	public void insert(SnsBoardRequestDTO dto) {

		//날짜별로 폴더를 생성해서 관리할 예정입니다.(yyyyMMdd)
		//날짜는 LocalDateTime과 DateTimeFormatter를 이용하세요.
		String fileLoca = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		System.out.println("폴더명: " + fileLoca);


		//기본 경로는 C:/test/upload로 사용 하겠습니다.
		String uploadPath = "C:/test/upload/" + fileLoca;

		//폴더가 존재하지 않다면 새롭게 생성해 주시라~
		File f = new File(uploadPath);
		if(!f.exists()) {
			f.mkdirs(); //s차이 -> s없: 폴더1개, s있: 폴더 여러 개
		}


		//저장될 파일명을 UUID를 이용한 파일명으로 저장합니다.
		//UUID가 제공하는 랜덤 문자열에 -을 제거해서 전부 사용하시면 됩니다.

		//원본명
		String fileRealName = dto.getFile().getOriginalFilename();

		//확장자
		String fileExtension = fileRealName.substring(fileRealName.lastIndexOf("."),fileRealName.length());
		System.out.println("확장자명: " + fileExtension);

		//가공 된 랜덤 파일명
		UUID uuid = UUID.randomUUID(); // static임
		String fileName = uuid.toString().replace("-", "") + fileExtension;
		System.out.println("랜덤 파일명: " + fileName);

		//날짜로 된 폴더명 + 파일 세이브
		File saveFile = new File(uploadPath + "/" + fileName);

		//실제 전달된 파일을 지정한 로컬 경로에 전송(transferTo) 하세요.
		try {
			dto.getFile().transferTo(saveFile);
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
		}

		//DB에 각각의 값을 저장하세요. (INSERT)
		//uploadPath -> "C:/test/upload"
		//fileLoca -> 날짜로 된 폴더명
		//fileName -> 랜덤 파일명
		//fileRealName -> 실제 파일명


		mapper.insert(SnsBoard.builder()
				.writer(dto.getWriter())
				.uploadPath(uploadPath)
				.fileRealName(fileRealName)
				.fileLoca(fileLoca)
				.fileName(fileName)
				.content(dto.getContent())
				.build());
	}

	public List<SnsBoardResponseDTO> getList(int page) {

		List<SnsBoardResponseDTO> dtoList = new ArrayList<>();

		List<SnsBoard> list = mapper.getList(Page.builder()
				.pageNo(page)
				.amount(3)
				.build());
		for(SnsBoard board : list) {
			dtoList.add(new SnsBoardResponseDTO(board));
		}

		return dtoList;
	}

	public SnsBoardResponseDTO getContent(int bno) {

		SnsBoard board = mapper.getDetail(bno);
		//SnsBoardResponseDTO godto = new SnsBoardResponseDTO();

		return new SnsBoardResponseDTO(board);

		//return new SnsBoardResponseDTO(SnsBoard.builder().bno(bno).);


	}

	public void delete(int bno) {
		//여기서 서버쪽에서 권한을 확인 해 주세요. (작성자와 로그인 중인 사용자의 id를 비교해서 일치하는지의 여부)
		//일치하지 않는다면 문자열 "noAuth" 리턴, 삭제 완료하면 "success" 리턴
		/*
		if(mapper.getDetail(bno).getWriter().equals(getContent(bno).getWriter())) {
			System.out.println("로그인 중인 사용자 id: " + mapper.getDetail(bno).getWriter());
			System.out.println("작성자 id: " + getContent(bno).getWriter());
			mapper.delete(bno);
			return "success";

		}else {
			return "noAuth";
		}
		 */

		//선생님
		mapper.delete(bno);;

	}

	public String searchLike(Map<String, String> params) {

		//눌른걸 취소한건지, 그냥 좋아요를 누른건지

		//물어본다: 매개 값을 그대로 넘겼을 때 조회한 결과를 count한 값이 0인가?
		if(mapper.searchLike(params) == 0) {
			//좋아요 처음 눌렀다.
			mapper.createLike(params);
			return "like";
		}else {
			//이미 누른 좋아요를 취소하고 싶다.
			mapper.deleteLike(params);
			return "delete";
		}
	}

	public List<Integer> likeList(String userId) {
		
		return mapper.likeList(userId);
		
	}

}

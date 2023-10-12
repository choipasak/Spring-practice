package com.spring.myweb.freeboard.service;

import java.util.List;

import org.springframework.ui.Model;

import com.spring.myweb.freeboard.DTO.FreeContentResponseDTO;
import com.spring.myweb.freeboard.DTO.FreeListResponseDTO;
import com.spring.myweb.freeboard.DTO.FreeRegistRequestDTO;
import com.spring.myweb.freeboard.DTO.FreeUpdateRequestDTO;
import com.spring.myweb.freeboard.entity.FreeBoard;

public interface IFreeBoardService {
	
	// 글 등록
	void regist(FreeRegistRequestDTO dto);
	
	// 글 목록
	List<FreeListResponseDTO> getList();
	
	// 상세 보기
	FreeContentResponseDTO getContent(int bno);
	
	// 수정
	void update(FreeUpdateRequestDTO dto);
	
	// 삭제
	void delete(int bno);


}

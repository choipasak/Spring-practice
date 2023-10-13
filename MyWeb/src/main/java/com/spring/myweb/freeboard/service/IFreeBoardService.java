package com.spring.myweb.freeboard.service;

import java.util.List;

import com.spring.myweb.freeboard.dto.FreeContentResponseDTO;
import com.spring.myweb.freeboard.dto.FreeListResponseDTO;
import com.spring.myweb.freeboard.dto.FreeRegistRequestDTO;
import com.spring.myweb.freeboard.dto.FreeUpdateRequestDTO;
import com.spring.myweb.freeboard.dto.page.Page;

public interface IFreeBoardService {
	
	// 글 등록
	void regist(FreeRegistRequestDTO dto);
	
	// 글 목록
	List<FreeListResponseDTO> getList(Page page);
	
	//총 게시물 개수
	int getTotal(Page page);
	
	// 상세 보기
	FreeContentResponseDTO getContent(int bno);
	
	// 수정
	void update(FreeUpdateRequestDTO dto);
	
	// 삭제
	void delete(int bno);


}

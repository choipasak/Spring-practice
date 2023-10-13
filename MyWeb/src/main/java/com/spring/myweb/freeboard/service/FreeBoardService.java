package com.spring.myweb.freeboard.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.myweb.freeboard.dto.FreeContentResponseDTO;
import com.spring.myweb.freeboard.dto.FreeListResponseDTO;
import com.spring.myweb.freeboard.dto.FreeRegistRequestDTO;
import com.spring.myweb.freeboard.dto.FreeUpdateRequestDTO;
import com.spring.myweb.freeboard.dto.page.Page;
import com.spring.myweb.freeboard.entity.FreeBoard;
import com.spring.myweb.freeboard.mapper.IFreeBoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FreeBoardService implements IFreeBoardService {

	private final IFreeBoardMapper mapper;
	
	@Override
	public void regist(FreeRegistRequestDTO dto) {
		mapper.regist(FreeBoard.builder()
								.title(dto.getTitle())
								.content(dto.getContent())
								.writer(dto.getWriter())
								.build());
	}

	@Override
	public List<FreeListResponseDTO> getList(Page page) {//List<FreeListResponseDTO>에 빨간줄: @Override에 어긋나기 때문 -> 해결: 부모 타입을 바꾼다.
		
		List<FreeListResponseDTO> dtoList = new ArrayList<>();
		List<FreeBoard> list = mapper.getList(page);
		//이걸 바로 return list; 하면 바로 entity를 리턴하는 것과 같음
		//그래서 DTO로 꾸며서 보내야함.
		for(FreeBoard board : list) {
			dtoList.add(new FreeListResponseDTO(board)); // dto가 board를 받아서
		}
		return dtoList;
	}
	
	@Override
	public int getTotal(Page page) {
		return mapper.getTotal(page);
	}

	@Override
	public FreeContentResponseDTO getContent(int bno) {
		//FreeBoard contentBoard = mapper.getContent(bno);
		//FreeContentResponseDTO condto = new FreeContentResponseDTO(contentBoard);
		
		//condto.add(new FreeContentResponseDTO(contentBoard));
		
		return new FreeContentResponseDTO(mapper.getContent(bno)); // 이렇게 한줄로 작성 가능!
	}

	@Override
	public void update(FreeUpdateRequestDTO dto) {

		mapper.update(FreeBoard.builder()
				.bno(dto.getBno())//dto에 담은 값을 freeboard에 넣어줬음
				.title(dto.getTitle())
				.content(dto.getContent())
				.build());
	}

	@Override
	public void delete(int bno) {
		mapper.delete(bno);
	}

}

package com.spring.myweb.freeboard.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.myweb.freeboard.DTO.FreeContentResponseDTO;
import com.spring.myweb.freeboard.DTO.FreeListResponseDTO;
import com.spring.myweb.freeboard.DTO.FreeRegistRequestDTO;
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
	public List<FreeListResponseDTO> getList() {//List<FreeListResponseDTO>에 빨간줄: @Override에 어긋나기 때문 -> 해결: 부모 타입을 바꾼다.
		
		List<FreeListResponseDTO> dtoList = new ArrayList<>();
		List<FreeBoard> list = mapper.getList();
		//이걸 바로 return list; 하면 바로 entity를 리턴하는 것과 같음
		//그래서 DTO로 꾸며서 보내야함.
		for(FreeBoard board : list) {
			dtoList.add(new FreeListResponseDTO(board)); // dto가 board를 받아서
		}
		return dtoList;
	}

	@Override
	public FreeContentResponseDTO getContent(int bno) {
		FreeBoard contentBoard = mapper.getContent(bno);
		FreeContentResponseDTO condto = new FreeContentResponseDTO(contentBoard);
		//condto.add(new FreeContentResponseDTO(contentBoard));
		
		
		return condto;
	}

	@Override
	public void update(FreeBoard freeBoard) {

	}

	@Override
	public void delete(int bno) {

	}

}

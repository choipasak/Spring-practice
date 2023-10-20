package com.spring.myweb.snsboard.service;

import org.springframework.stereotype.Service;

import com.spring.myweb.snsboard.mapper.ISnsBoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SnsServiceBoard {
	
	private final ISnsBoardMapper mapper;

}

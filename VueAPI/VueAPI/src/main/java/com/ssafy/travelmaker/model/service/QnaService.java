package com.ssafy.travelmaker.model.service;

import java.util.List;

import com.ssafy.travelmaker.model.BoardDto;
import com.ssafy.travelmaker.model.BoardParameterDto;
import com.ssafy.util.PageNavigation;

public interface QnaService {
	public boolean writeArticle(BoardDto boardDto) throws Exception;
	public List<BoardDto> listArticle(BoardParameterDto boardParameterDto) throws Exception;
	public PageNavigation makePageNavigation(BoardParameterDto boardParameterDto) throws Exception;
	
	public BoardDto getArticle(int articleno) throws Exception;
	public void updateHit(int articleno) throws Exception;
	public boolean modifyArticle(BoardDto boardDto) throws Exception;
	public boolean deleteArticle(int articleno) throws Exception;
}

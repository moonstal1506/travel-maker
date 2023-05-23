package com.ssafy.travelmaker.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.travelmaker.model.BoardDto;
import com.ssafy.travelmaker.model.BoardParameterDto;
import com.ssafy.travelmaker.model.CommentDto;
import com.ssafy.util.PageNavigation;

@Mapper
public interface BoardMapper {
	
	public int writeArticle(BoardDto boardDto) throws Exception;
	public List<BoardDto> listArticle(BoardParameterDto boardParameterDto) throws Exception;
	public PageNavigation makePageNavigation(BoardParameterDto boardParameterDto) throws Exception;
	public int getTotalCount(BoardParameterDto boardParameterDto);
	public void deleteMemo(int articleno);
	public BoardDto getArticle(int articleno) throws Exception;
	public void updateHit(int articleno) throws Exception;
	public int modifyArticle(BoardDto boardDto) throws Exception;
	public int deleteArticle(int articleno) throws Exception;
	public CommentDto getComment(int articleno) throws SQLException;
	public boolean writeComment(CommentDto commentDto) throws Exception;

}
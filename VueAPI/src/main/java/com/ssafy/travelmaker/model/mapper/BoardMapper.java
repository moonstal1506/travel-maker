package com.ssafy.travelmaker.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.travelmaker.model.BoardDto;
import com.ssafy.travelmaker.model.BoardParameterDto;
import com.ssafy.travelmaker.model.CommentDto;

@Mapper
public interface BoardMapper {
	
	public int writeArticle(BoardDto boardDto) throws SQLException;
	public List<BoardDto> listArticle(BoardParameterDto boardParameterDto) throws SQLException;
	public int getTotalCount(BoardParameterDto boardParameterDto) throws SQLException;
	public BoardDto getArticle(int articleno) throws SQLException;
	public void updateHit(int articleno) throws SQLException;
	public int modifyArticle(BoardDto boardDto) throws SQLException;
	public void deleteMemo(int articleno) throws SQLException;
	public int deleteArticle(int articleno) throws SQLException;
	
<<<<<<< HEAD
	public CommentDto getComment(int articleno) throws SQLException;
=======
	
>>>>>>> 1c28d632974e234bc3f07dede7a2a385c31b1695
}
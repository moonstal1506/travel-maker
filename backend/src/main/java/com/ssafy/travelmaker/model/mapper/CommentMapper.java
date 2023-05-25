package com.ssafy.travelmaker.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.travelmaker.model.CommentDto;

@Mapper
public interface CommentMapper {
	
	public int writeComment(CommentDto commentDto) throws SQLException;
	public List<CommentDto> listComment(int articleno) throws SQLException;
	public int deleteComment(int articleno, int commentId);
}
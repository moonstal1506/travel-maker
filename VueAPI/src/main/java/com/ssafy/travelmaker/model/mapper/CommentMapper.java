package com.ssafy.travelmaker.model.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.travelmaker.model.CommentDto;

@Mapper
public interface CommentMapper {
	
	public int writeComment(CommentDto commentDto) throws SQLException;
}
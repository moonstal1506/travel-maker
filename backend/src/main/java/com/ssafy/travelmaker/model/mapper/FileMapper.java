package com.ssafy.travelmaker.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.travelmaker.model.BoardDto;
import com.ssafy.travelmaker.model.FileInfoDto;

@Mapper
public interface FileMapper {
	
	void insert(BoardDto boardDto) throws SQLException;
	List<FileInfoDto> search(int articleno) throws SQLException;
	void remove(int articleno) throws SQLException;
}
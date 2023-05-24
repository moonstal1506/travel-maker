package com.ssafy.travelmaker.model.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.travelmaker.exception.TripException;
import com.ssafy.travelmaker.model.TripDto;
import com.ssafy.travelmaker.model.mapper.TripMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TripServiceImpl implements TripService {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<TripDto> search(TripDto trip) {
		try {
			return sqlSession.getMapper(TripMapper.class).search(trip);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TripException(e.getMessage());
		}
	}
	
	@Override
	public List<TripDto> main(TripDto trip) {
		try {
			return sqlSession.getMapper(TripMapper.class).main(trip);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TripException(e.getMessage());
		}
	}
}

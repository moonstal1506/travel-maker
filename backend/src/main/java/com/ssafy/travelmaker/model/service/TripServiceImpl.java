package com.ssafy.travelmaker.model.service;

import com.ssafy.travelmaker.exception.TripException;
import com.ssafy.travelmaker.model.MainTripDto;
import com.ssafy.travelmaker.model.TripDto;
import com.ssafy.travelmaker.model.mapper.TripMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TripServiceImpl implements TripService {

    private final SqlSession sqlSession;

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
    public List<MainTripDto> findAll() {
        try {
            return sqlSession.getMapper(TripMapper.class).findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TripException(e.getMessage());
        }
    }
}

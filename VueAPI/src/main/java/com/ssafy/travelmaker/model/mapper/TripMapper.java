package com.ssafy.travelmaker.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.travelmaker.model.TripDto;

@Mapper
public interface TripMapper {

	List<TripDto> search(TripDto trip) throws SQLException;
	List<TripDto> main(TripDto trip) throws SQLException;
}

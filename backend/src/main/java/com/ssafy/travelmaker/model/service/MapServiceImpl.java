package com.ssafy.travelmaker.model.service;



import java.util.List;

import com.ssafy.travelmaker.model.mapper.MapMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.travelmaker.model.SidoGugunCodeDto;


@RequiredArgsConstructor
@Service
public class MapServiceImpl implements MapService {
	
	private final SqlSession sqlSession;

	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return sqlSession.getMapper(MapMapper.class).getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return sqlSession.getMapper(MapMapper.class).getGugunInSido(sido);
	}
}

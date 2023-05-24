package com.ssafy.travelmaker.model.service;

import com.ssafy.travelmaker.exception.BoardException;
import com.ssafy.travelmaker.exception.TripException;
import com.ssafy.travelmaker.model.*;
import com.ssafy.travelmaker.model.mapper.*;
import com.ssafy.util.PageNavigation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class PlanServiceImpl implements PlanService {

	private final SqlSession sqlSession;

	@Transactional
	@Override
	public boolean register(PlanDto planDto) throws Exception {
		try {
			int planId= sqlSession.getMapper(PlanMapper.class).register(planDto);
			for (PlanTripDto plan : planDto.getPlans()) {
				sqlSession.getMapper(PlanTripMapper.class).register(new PlanTripDto(planId, plan.getContentId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TripException("여행 계획 등록 중 오류 발생");
		}
		return true;
	}
}

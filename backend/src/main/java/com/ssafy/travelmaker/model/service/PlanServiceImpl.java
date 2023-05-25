package com.ssafy.travelmaker.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.travelmaker.exception.TripException;
import com.ssafy.travelmaker.model.PlanDto;
import com.ssafy.travelmaker.model.PlanTripDto;
import com.ssafy.travelmaker.model.mapper.PlanMapper;
import com.ssafy.travelmaker.model.mapper.PlanTripMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlanServiceImpl implements PlanService {

	private final SqlSession sqlSession;

	@Transactional
	@Override
	public boolean register(PlanDto planDto) {
		try {
			sqlSession.getMapper(PlanMapper.class).register(planDto);
			int planId = planDto.getPlanId();
			for (PlanTripDto plan : planDto.getPlans()) {
				sqlSession.getMapper(PlanTripMapper.class).register(new PlanTripDto(plan.getContentId(), planId));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TripException("여행 계획 등록 중 오류 발생");
		}
		return true;
	}

	@Override
	public List<PlanDto> listPlan() {
		return sqlSession.getMapper(PlanMapper.class).listPlan();
	}
	
	@Override
	public PlanDto getPlan(int planId) {
		return sqlSession.getMapper(PlanMapper.class).getPlan(planId);
	}

	@Override
	public boolean deletePlan(int planId) {
		return sqlSession.getMapper(PlanMapper.class).deletePlan(planId);
	}
}

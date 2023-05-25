package com.ssafy.travelmaker.model.mapper;

import com.ssafy.travelmaker.model.PlanDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanMapper {
	int register(PlanDto planDto) throws Exception;
    List<PlanDto> listPlan();
	PlanDto getPlan(int planId);
	boolean deletePlan(int planId);
}
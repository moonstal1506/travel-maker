package com.ssafy.travelmaker.model.service;

import com.ssafy.travelmaker.model.PlanDto;

import java.util.List;

public interface PlanService {
    boolean register(PlanDto planDto);
    List<PlanDto> listPlan();
	PlanDto getPlan(int planId);
	boolean deletePlan(int planId);
}

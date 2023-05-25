package com.ssafy.travelmaker.model.service;

import com.ssafy.travelmaker.model.PlanDto;

import java.util.List;

public interface PlanService {
    boolean register(PlanDto planDto) throws Exception;
    List<PlanDto> listPlan();
}

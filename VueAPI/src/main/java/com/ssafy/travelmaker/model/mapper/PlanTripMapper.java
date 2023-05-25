package com.ssafy.travelmaker.model.mapper;

import com.ssafy.travelmaker.model.PlanTripDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlanTripMapper {
	boolean register(PlanTripDto planTripDto) throws Exception;
}
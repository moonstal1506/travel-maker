package com.ssafy.travelmaker.model.mapper;

import com.ssafy.travelmaker.model.PlanDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlanMapper {
	int register(PlanDto planDto) throws Exception;
}
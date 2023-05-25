package com.ssafy.travelmaker.model.service;

import java.util.List;

import com.ssafy.travelmaker.model.SidoGugunCodeDto;

public interface MapService {

	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;

}

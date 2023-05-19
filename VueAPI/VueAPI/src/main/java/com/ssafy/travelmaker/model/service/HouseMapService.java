package com.ssafy.travelmaker.model.service;

import java.util.List;

import com.ssafy.travelmaker.model.HouseInfoDto;
import com.ssafy.travelmaker.model.SidoGugunCodeDto;

public interface HouseMapService {

	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	List<HouseInfoDto> getDongInGugun(String gugun) throws Exception;
	List<HouseInfoDto> getAptInDong(String dong) throws Exception;
	
}

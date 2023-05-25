package com.ssafy.travelmaker.model.service;

import java.util.List;

import com.ssafy.travelmaker.model.HotPlaceDto;
import com.ssafy.travelmaker.model.HotPlaceParameterDto;
import com.ssafy.util.PageNavigation;

public interface HotPlaceService {
    List<HotPlaceDto> listArticle(HotPlaceParameterDto hotplaceParameterDto) throws Exception;
    PageNavigation makePageNavigation(HotPlaceParameterDto hotplaceParameterDto) throws Exception;
    HotPlaceDto getArticle(int articleno) throws Exception;
    void updateHit(int articleno) throws Exception;
    boolean modifyArticle(HotPlaceDto hotplaceDto) throws Exception;
    boolean deleteArticle(int articleno) throws Exception;
	boolean writeArticle(HotPlaceDto hotplaceDto) throws Exception;
	
}

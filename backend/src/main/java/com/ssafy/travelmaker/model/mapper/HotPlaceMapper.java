package com.ssafy.travelmaker.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.travelmaker.model.HotPlaceDto;
import com.ssafy.travelmaker.model.HotPlaceParameterDto;
import com.ssafy.util.PageNavigation;

@Mapper
public interface HotPlaceMapper {
	
	public int writeArticle(HotPlaceDto hotplaceDto) throws Exception;
	public List<HotPlaceDto> listArticle(HotPlaceParameterDto hotplaceParameterDto) throws Exception;
	public PageNavigation makePageNavigation(HotPlaceParameterDto hotplaceParameterDto) throws Exception;
	public int getTotalCount(HotPlaceParameterDto hotplaceParameterDto);
	public void deleteMemo(int articleno);
	public HotPlaceDto getArticle(int articleno) throws Exception;
	public void updateHit(int articleno) throws Exception;
	public int modifyArticle(HotPlaceDto hotplaceDto) throws Exception;
	public int deleteArticle(int articleno) throws Exception;

}
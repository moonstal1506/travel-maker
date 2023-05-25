package com.ssafy.travelmaker.model.service;

import java.io.File;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.travelmaker.exception.HotPlaceException;
import com.ssafy.travelmaker.model.HotPlaceDto;
import com.ssafy.travelmaker.model.HotPlaceParameterDto;
import com.ssafy.travelmaker.model.mapper.HotPlaceMapper;
import com.ssafy.util.PageNavigation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HotPlaceServiceImpl implements HotPlaceService {
	
	@Autowired
	private SqlSession sqlSession;

	@Transactional
	@Override
	public boolean writeArticle(HotPlaceDto hotplaceDto) throws Exception {
		File[] realFile = null;
		int size=0;
		boolean isSuccess=false;
		try {
			if(hotplaceDto.getSubject() == null || hotplaceDto.getContent() == null) {
				throw new Exception();
			}
			isSuccess=sqlSession.getMapper(HotPlaceMapper.class).writeArticle(hotplaceDto)==1;
////			file 정보 처리 
//			MultipartFile[] fileups = hotplaceDto.getFileup();
//			if(fileups != null) {
//				size  = fileups.length;
//				if(size>0) {
//					ArrayList<FileInfoDto> files = new ArrayList<FileInfoDto>(size);
//					realFile = new File[size];
//					int idx = 0;
//					for (MultipartFile file : fileups) {
//						String rfileName = file.getOriginalFilename();    //사용자가 upload한 파일 이름을 추출 
//						String sfileName = String.format("%d%s", System.currentTimeMillis(), rfileName);
//						String savePath = String.format("%s/%s", uploadPath, sfileName);
//						log.debug("insert file info  rfilename:{} sfilename:{}", rfileName, sfileName);
//						realFile[idx] = new File(savePath);
//						
//						file.transferTo(realFile[idx++]);  //파일을 지정한 경로에 저장 
//						//file 정보를 DB에 저장 
//						files.add(new FileInfoDto( rfileName, sfileName));
//					}
//					hotplaceDto.setFiles(files);
//					sqlSession.getMapper(FileMapper.class).insert(hotplaceDto);
//
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
			//오류가 났기 때문에  모든 데이타는 rollback이 되고 저장한 파일 정보도 삭제해야 한다. 
//			fileRemove(realFile);
			throw new HotPlaceException("책 정보 등록 중 오류 발생 ");
		}
		return isSuccess;
	}

	@Override
	public List<HotPlaceDto> listArticle(HotPlaceParameterDto hotplaceParameterDto) throws Exception {
		int start = hotplaceParameterDto.getPg() == 0 ? 0 : (hotplaceParameterDto.getPg() - 1) * hotplaceParameterDto.getSpp();
		hotplaceParameterDto.setStart(start);
		return sqlSession.getMapper(HotPlaceMapper.class).listArticle(hotplaceParameterDto);
	}

	@Override
	public PageNavigation makePageNavigation(HotPlaceParameterDto hotplaceParameterDto) throws Exception {
		int naviSize = 5;
		PageNavigation pageNavigation = new PageNavigation();
		pageNavigation.setCurrentPage(hotplaceParameterDto.getPg());
		pageNavigation.setNaviSize(naviSize);
		int totalCount = sqlSession.getMapper(HotPlaceMapper.class).getTotalCount(hotplaceParameterDto);//총글갯수  269
		pageNavigation.setTotalCount(totalCount);  
		int totalPageCount = (totalCount - 1) / hotplaceParameterDto.getSpp() + 1;//27
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = hotplaceParameterDto.getPg() <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < hotplaceParameterDto.getPg();
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		return pageNavigation;
	}

	@Override
	public HotPlaceDto getArticle(int articleno) throws Exception {
		return sqlSession.getMapper(HotPlaceMapper.class).getArticle(articleno);
	}
	
	@Override
	public void updateHit(int articleno) throws Exception {
		sqlSession.getMapper(HotPlaceMapper.class).updateHit(articleno);
	}

	@Override
	@Transactional
	public boolean modifyArticle(HotPlaceDto hotplaceDto) throws Exception {
		return sqlSession.getMapper(HotPlaceMapper.class).modifyArticle(hotplaceDto) == 1;
	}

	@Override
	@Transactional
	public boolean deleteArticle(int articleno) throws Exception {
		sqlSession.getMapper(HotPlaceMapper.class).deleteMemo(articleno);
		return sqlSession.getMapper(HotPlaceMapper.class).deleteArticle(articleno) == 1;
	}


}

package com.ssafy.travelmaker.model.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.travelmaker.exception.BoardException;
import com.ssafy.travelmaker.model.BoardDto;
import com.ssafy.travelmaker.model.BoardParameterDto;
import com.ssafy.travelmaker.model.CommentDto;
import com.ssafy.travelmaker.model.FileInfoDto;
import com.ssafy.travelmaker.model.mapper.BoardMapper;
import com.ssafy.travelmaker.model.mapper.CommentMapper;
import com.ssafy.travelmaker.model.mapper.FileMapper;
import com.ssafy.util.PageNavigation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private SqlSession sqlSession;

	@Transactional
	@Override
	public boolean writeArticle(BoardDto boardDto, String uploadPath) throws Exception {
		File[] realFile = null;
		int size=0;
		boolean isSuccess=false;
		try {
			if(boardDto.getSubject() == null || boardDto.getContent() == null) {
				throw new Exception();
			}
			isSuccess=sqlSession.getMapper(BoardMapper.class).writeArticle(boardDto)==1;
//			file 정보 처리 
			MultipartFile[] fileups = boardDto.getFileup();
			if(fileups != null) {
				size  = fileups.length;
				if(size>0) {
					ArrayList<FileInfoDto> files = new ArrayList<FileInfoDto>(size);
					realFile = new File[size];
					int idx = 0;
					for (MultipartFile file : fileups) {
						String rfileName = file.getOriginalFilename();    //사용자가 upload한 파일 이름을 추출 
						String sfileName = String.format("%d%s", System.currentTimeMillis(), rfileName);
						String savePath = String.format("%s/%s", uploadPath, sfileName);
						log.debug("insert file info  rfilename:{} sfilename:{}", rfileName, sfileName);
						realFile[idx] = new File(savePath);
						
						file.transferTo(realFile[idx++]);  //파일을 지정한 경로에 저장 
						//file 정보를 DB에 저장 
						files.add(new FileInfoDto( rfileName, sfileName));
					}
					boardDto.setFiles(files);
					sqlSession.getMapper(FileMapper.class).insert(boardDto);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			//오류가 났기 때문에  모든 데이타는 rollback이 되고 저장한 파일 정보도 삭제해야 한다. 
//			fileRemove(realFile);
			throw new BoardException("책 정보 등록 중 오류 발생 ");
		}
		return isSuccess;
	}

	@Override
	public List<BoardDto> listArticle(BoardParameterDto boardParameterDto) throws Exception {
		int start = boardParameterDto.getPg() == 0 ? 0 : (boardParameterDto.getPg() - 1) * boardParameterDto.getSpp();
		boardParameterDto.setStart(start);
		return sqlSession.getMapper(BoardMapper.class).listArticle(boardParameterDto);
	}

	@Override
	public PageNavigation makePageNavigation(BoardParameterDto boardParameterDto) throws Exception {
		int naviSize = 5;
		PageNavigation pageNavigation = new PageNavigation();
		pageNavigation.setCurrentPage(boardParameterDto.getPg());
		pageNavigation.setNaviSize(naviSize);
		int totalCount = sqlSession.getMapper(BoardMapper.class).getTotalCount(boardParameterDto);//총글갯수  269
		pageNavigation.setTotalCount(totalCount);  
		int totalPageCount = (totalCount - 1) / boardParameterDto.getSpp() + 1;//27
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = boardParameterDto.getPg() <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < boardParameterDto.getPg();
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		return pageNavigation;
	}

	@Override
	public BoardDto getArticle(int articleno) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).getArticle(articleno);
	}
	
	@Override
	public void updateHit(int articleno) throws Exception {
		sqlSession.getMapper(BoardMapper.class).updateHit(articleno);
	}

	@Override
	@Transactional
	public boolean modifyArticle(BoardDto boardDto) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).modifyArticle(boardDto) == 1;
	}

	@Override
	@Transactional
	public boolean deleteArticle(int articleno) throws Exception {
		sqlSession.getMapper(BoardMapper.class).deleteMemo(articleno);
		return sqlSession.getMapper(BoardMapper.class).deleteArticle(articleno) == 1;
	}

	@Override
	public boolean writeComment(CommentDto commentDto) throws Exception {
		return sqlSession.getMapper(CommentMapper.class).writeComment(commentDto) == 1;
	}

	@Override
	public List<CommentDto> listComment(int articleno) throws Exception {
		return sqlSession.getMapper(CommentMapper.class).listComment(articleno);
	}

	@Override
	public boolean deleteComment(int articleno, int commentId) {
		return sqlSession.getMapper(CommentMapper.class).deleteComment(articleno,commentId) == 1;
	}

}

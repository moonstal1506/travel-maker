package com.ssafy.travelmaker.model.service;

import java.util.List;

import com.ssafy.travelmaker.model.BoardDto;
import com.ssafy.travelmaker.model.BoardParameterDto;
import com.ssafy.travelmaker.model.CommentDto;
import com.ssafy.util.PageNavigation;

public interface BoardService {
    List<BoardDto> listArticle(BoardParameterDto boardParameterDto) throws Exception;
    PageNavigation makePageNavigation(BoardParameterDto boardParameterDto) throws Exception;
    BoardDto getArticle(int articleno) throws Exception;
    void updateHit(int articleno) throws Exception;
    boolean modifyArticle(BoardDto boardDto) throws Exception;
    boolean deleteArticle(int articleno) throws Exception;

    boolean writeComment(CommentDto commentDto) throws Exception;
    List<CommentDto> listComment(int articleno) throws Exception;
    boolean deleteComment(int articleno, int commentId);
	boolean writeArticle(BoardDto boardDto, String uploadPath) throws Exception;
	
}

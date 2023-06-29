package com.ssafy.travelmaker.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.travelmaker.model.HotPlaceDto;
import com.ssafy.travelmaker.model.HotPlaceParameterDto;
import com.ssafy.travelmaker.model.service.HotPlaceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//http://localhost:9999/vue/swagger-ui.html
//@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequiredArgsConstructor
@RequestMapping("/hotplace")
@Api("게시판 컨트롤러  API V1")
public class HotPlaceController {

	private static final Logger logger = LoggerFactory.getLogger(HotPlaceController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	private final HotPlaceService hotplaceService;

	@ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> writeArticle(@ModelAttribute @ApiParam(value = "게시글 정보.", required = true) HotPlaceDto hotplaceDto) throws Exception {
		System.out.println(hotplaceDto);
		logger.info("writeArticle - 호출");
		if (hotplaceService.writeArticle(hotplaceDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<HotPlaceDto>> listArticle(
			@ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) HotPlaceParameterDto hotplaceParameterDto)
			throws Exception {
		logger.info("listArticle - 호출");
		System.out.println(hotplaceParameterDto);
		return new ResponseEntity<List<HotPlaceDto>>(hotplaceService.listArticle(hotplaceParameterDto), HttpStatus.OK);
	}

	@ApiOperation(value = "게시판 글보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = HotPlaceDto.class)
	@GetMapping("/{articleno}")
	public ResponseEntity<HotPlaceDto> getArticle(
			@PathVariable("articleno") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleno)
			throws Exception {
		logger.info("getArticle - 호출 : " + articleno);
		hotplaceService.updateHit(articleno);
		return new ResponseEntity<HotPlaceDto>(hotplaceService.getArticle(articleno), HttpStatus.OK);
	}

	@ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> modifyArticle(
			@RequestBody @ApiParam(value = "수정할 글정보.", required = true) HotPlaceDto hotplaceDto) throws Exception {
		logger.info("modifyArticle - 호출 {}", hotplaceDto);

		if (hotplaceService.modifyArticle(hotplaceDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}

	@ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{articleno}")
	public ResponseEntity<String> deleteArticle(
			@PathVariable("articleno") @ApiParam(value = "살제할 글의 글번호.", required = true) int articleno)
			throws Exception {
		logger.info("deleteArticle - 호출");
		if (hotplaceService.deleteArticle(articleno)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

}
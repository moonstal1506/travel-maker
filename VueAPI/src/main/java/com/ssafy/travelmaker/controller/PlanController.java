package com.ssafy.travelmaker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.travelmaker.model.BoardDto;
import com.ssafy.travelmaker.model.PlanDto;
import com.ssafy.travelmaker.model.service.PlanService;

import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/plan")
@RestController
public class PlanController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private final PlanService planService;

	@PostMapping
	public ResponseEntity<?> register(@RequestBody PlanDto planDto) throws Exception {
		System.out.println(planDto);
		if (planService.register(planDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.CREATED);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@GetMapping
	public ResponseEntity<List<PlanDto>> listPlan() throws Exception {
		System.out.println(planService.listPlan().size());
		return new ResponseEntity<List<PlanDto>>(planService.listPlan(), HttpStatus.OK);
	}
	
	@GetMapping("/{planId}")
	public ResponseEntity<PlanDto> getPlan(@PathVariable int planId) throws Exception {
		return new ResponseEntity<PlanDto>(planService.getPlan(planId), HttpStatus.OK);
	}
	
	@DeleteMapping("/{planId}")
	public ResponseEntity<String> deleteArticle(@PathVariable("planId")  int planId)
			throws Exception {
		if (planService.deletePlan(planId)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}

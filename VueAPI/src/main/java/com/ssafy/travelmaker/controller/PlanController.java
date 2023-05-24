package com.ssafy.travelmaker.controller;

import com.ssafy.travelmaker.model.PlanDto;
import com.ssafy.travelmaker.model.TripDto;
import com.ssafy.travelmaker.model.service.PlanService;
import com.ssafy.travelmaker.model.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}

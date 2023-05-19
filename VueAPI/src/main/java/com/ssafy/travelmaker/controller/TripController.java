package com.ssafy.travelmaker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.travelmaker.model.TripDto;
import com.ssafy.travelmaker.model.service.TripService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/trip")
@Controller
public class TripController {

	private final TripService service;
	
	@ResponseBody
	@GetMapping("/data")
	public ResponseEntity<?> data(TripDto trip) {
		System.out.println(trip);
		return new ResponseEntity<List>(service.search(trip), HttpStatus.OK);
	}

}

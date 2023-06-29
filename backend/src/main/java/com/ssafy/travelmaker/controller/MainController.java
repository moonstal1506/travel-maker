package com.ssafy.travelmaker.controller;

import java.util.List;

import com.ssafy.travelmaker.model.DirectionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.travelmaker.model.TripDto;
import com.ssafy.travelmaker.model.service.TripService;

import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MainController {

	private final TripService service;

	@GetMapping
	public ResponseEntity<?> main(DirectionDto direction) {
		log.info(String.valueOf(direction));
		return new ResponseEntity<List>(service.main(direction), HttpStatus.OK);
	}
}

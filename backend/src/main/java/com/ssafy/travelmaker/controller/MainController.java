package com.ssafy.travelmaker.controller;

import java.util.List;

import com.ssafy.travelmaker.model.LocationDto;
import com.ssafy.travelmaker.model.service.DirectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MainController {

	private final DirectionService directionService;

	@GetMapping
	public ResponseEntity<?> main(LocationDto location) {
		log.info(String.valueOf(location));
		System.out.println(location);
		return new ResponseEntity<List>(directionService.buildTripList(location), HttpStatus.OK);
	}
}

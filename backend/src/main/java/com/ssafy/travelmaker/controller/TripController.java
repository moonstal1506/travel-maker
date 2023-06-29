package com.ssafy.travelmaker.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.travelmaker.model.TripDto;
import com.ssafy.travelmaker.model.service.TripService;

import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/trip")
@RestController
public class TripController {

	private final TripService tripService;

	@GetMapping("/data")
	public ResponseEntity<?> data(TripDto trip) {
		log.info(String.valueOf(trip));
		return new ResponseEntity<List>(tripService.search(trip), HttpStatus.OK);
	}

}

package com.ssafy.travelmaker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.travelmaker.model.TripDto;
import com.ssafy.travelmaker.model.service.TripService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class HomeController {

	private final TripService service;

	@GetMapping
	public ResponseEntity<?> main(TripDto trip) {
		System.out.println(trip);
		return new ResponseEntity<List>(service.main(trip), HttpStatus.OK);
	}
}

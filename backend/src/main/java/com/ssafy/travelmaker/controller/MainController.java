package com.ssafy.travelmaker.controller;

import com.ssafy.travelmaker.model.LocationDto;
import com.ssafy.travelmaker.model.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MainController {

    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<?> main(LocationDto location) {
        return new ResponseEntity<List>(locationService.buildTripList(location), HttpStatus.OK);
    }
}

package com.ssafy.travelmaker.model.service;

import java.util.List;

import com.ssafy.travelmaker.model.DirectionDto;
import com.ssafy.travelmaker.model.MainTripDto;
import com.ssafy.travelmaker.model.TripDto;

public interface TripService {
	public List<TripDto> search(TripDto trip);
	public List<MainTripDto> main(DirectionDto direction);
}

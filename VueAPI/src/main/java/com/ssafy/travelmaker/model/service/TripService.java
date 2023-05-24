package com.ssafy.travelmaker.model.service;

import java.util.List;

import com.ssafy.travelmaker.model.TripDto;

public interface TripService {
	public List<TripDto> search(TripDto trip);
	public List<TripDto> main(TripDto trip);
}

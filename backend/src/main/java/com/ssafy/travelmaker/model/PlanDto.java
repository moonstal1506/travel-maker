package com.ssafy.travelmaker.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PlanDto implements Serializable {
	private int planId;
	private String userid;
	private String planTitle;
	private String date;
	private String content;
	private List<PlanTripDto> plans;
	private List<TripDto> trips;
}

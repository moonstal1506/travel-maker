package com.ssafy.travelmaker.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class PlanTripDto implements Serializable {
	private int planTripId;
	private int contentId;
	private int planId;

	public PlanTripDto(int contentId, int planId) {
		this.contentId = contentId;
		this.planId = planId;
	}
}

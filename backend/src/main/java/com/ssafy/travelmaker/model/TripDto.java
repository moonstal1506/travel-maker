package com.ssafy.travelmaker.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class TripDto implements Serializable {
	private int contentId;
	private int contentTypeId;
	private String addr1;
	private String addr2;
	private String first_image;
	private String first_image2;
	private int gugunCode;
	private double latitude;
	private double longitude;
	private String mlevel;
	private int readcount;
	private int sidoCode;
	private String tel;
	private String title;
	private String zipcode;
	private String overview;
}

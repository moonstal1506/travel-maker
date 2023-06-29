package com.ssafy.travelmaker.model;

import lombok.Data;

@Data
public class MainTripDto {
    private int contentId;
    private String title;
    private String first_image;
    private String addr1;
    private String addr2;
    private double latitude;
    private double longitude;
    private double distance;
}

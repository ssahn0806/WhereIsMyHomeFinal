package com.ssafy.house.model.dto;

import lombok.Data;

@Data
public class Place {
	private int no;
	private String name;
	private String subname;
	private String categorycode;
	private String categoryname;
	private String dongcode;
	private String address;
	private String lng;
	private String lat;
	private double distance;
}

package com.ssafy.house.model.dto;

import lombok.Data;

@Data
public class HouseDeal {
	private int no;
	private int aptCode;
	private String dealAmount;
	private int dealYear;
	private int dealMonth;
	private int dealDay;
	private String area;
	private String floor;
	private String type;
	private String rentMoney;

}

package com.ssafy.house.model.dto;

import lombok.Data;

@Data
public class QnA {
	private int articleNo;
	private String userId;
	private String subject;
	private String content;
	private int hit;
	private String registerTime;
	
	
}

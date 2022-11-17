package com.ssafy.house.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private String userid;
	private String userpw;
	private String username;
	private String userlevel;
	private String userfavdongcode;
	
		
}

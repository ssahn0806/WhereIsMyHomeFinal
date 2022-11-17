package com.ssafy.house.model.dto;

public class DataInfo {
	//data를 contentType으로 만들어 주세요.
	private String contentType;
	private Object data;
	
	public DataInfo(String contentType, Object data) {
		super();
		this.contentType = contentType;
		this.data = data;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}

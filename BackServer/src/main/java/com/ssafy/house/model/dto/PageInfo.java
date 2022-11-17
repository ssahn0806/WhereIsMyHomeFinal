package com.ssafy.house.model.dto;

public class PageInfo {
	
	private boolean forward;
	private String page;
	
	public PageInfo(boolean forward, String page) {
		super();
		this.forward = forward;
		this.page = page;
	}

	public PageInfo(String page) {
		super();
		this.page = page;
	}

	public boolean isForward() {
		return forward;
	}

	public void setForward(boolean forward) {
		this.forward = forward;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	
	
}
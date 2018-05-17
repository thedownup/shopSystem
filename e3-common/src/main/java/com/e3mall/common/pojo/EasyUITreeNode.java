package com.e3mall.common.pojo;

import java.io.Serializable;

public class EasyUITreeNode implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private long id;
	private String text;
	private String state;
	
	public long getId() {
		return id;
	}
	public String getState() {
		return state;
	}
	public String getText() {
		return text;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setText(String text) {
		this.text = text;
	}
}

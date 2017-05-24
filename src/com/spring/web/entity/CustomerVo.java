package com.spring.web.entity;

public class CustomerVo {

	public CustomerVo() {
		// TODO Auto-generated constructor stub
	}
	
	private String name;

	// Setter & Getter
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "CustomerVo [name=" + name + "]";
	}

}

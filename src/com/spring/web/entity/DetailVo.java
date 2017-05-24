package com.spring.web.entity;

public class DetailVo {

	public DetailVo() {
		// TODO Auto-generated constructor stub
	}

	private String orderId;
	private int goodId;
	private int qty;
	
	// Setter & Getter
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getGoodId() {
		return goodId;
	}
	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	@Override
	public String toString() {
		return "DetailVo [orderId=" + orderId + ", goodId=" + goodId + ", qty=" + qty + "]";
	}

}

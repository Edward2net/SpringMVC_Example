package com.spring.web.entity;

import java.util.Date;
import java.util.List;


public class MasterVo {

	public MasterVo() {
		// TODO Auto-generated constructor stub
	}
	
	private String orderId;
	private Date orderDate;
	private List<DetailVo> detailList;
	
	// Setter & Getter
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public List<DetailVo> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<DetailVo> detailList) {
		this.detailList = detailList;
	}
	
	@Override
	public String toString() {
		return "MasterVo [orderId=" + orderId + ", orderDate=" + orderDate + ", detailList=" + detailList + "]";
	}

}

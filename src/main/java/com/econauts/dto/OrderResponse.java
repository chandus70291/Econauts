package com.econauts.dto;

public class OrderResponse {

    private Long orderId;
    private double totalAmount;
    private String status;

    public OrderResponse(Long orderId, double totalAmount, String status) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.status = status;
    }

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    
}
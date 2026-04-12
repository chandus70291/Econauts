package com.econauts.dto;

import java.util.List;

public class CartSummary {

    private List<CartResponse> items;
    private double grandTotal;
    
    public CartSummary(List<CartResponse> items, double grandTotal) 
    {
	super();
	this.items = items;
	this.grandTotal = grandTotal;
    }
public List<CartResponse> getItems() {
	return items;
}
public void setItems(List<CartResponse> items) {
	this.items = items;
}
public double getGrandTotal() {
	return grandTotal;
}
public void setGrandTotal(double grandTotal) {
	this.grandTotal = grandTotal;
}
}

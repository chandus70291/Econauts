package com.econauts.Entity;

import jakarta.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private String productName;
    private double price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    public OrderItem() {}

    
	public OrderItem(Long id, Long productId, String productName, double price, int quantity, Orders order) {
		super();
		this.id = id;
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
		this.order = order;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

    
}
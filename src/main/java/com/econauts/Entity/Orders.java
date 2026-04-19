package com.econauts.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private double totalAmount;

    // ✅ Order Status ENUM
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // ✅ Payment Status ENUM
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    // ✅ Razorpay fields
    private String razorpayOrderId;
    private String razorpayPaymentId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // ✅ ENUMS (clean & structured)
    public enum OrderStatus {
        PENDING,
        PLACED,
        CONFIRMED,
        SHIPPED,
        DELIVERED,
        CANCELLED
    }

    public enum PaymentStatus {
        PENDING,
        SUCCESS,
        FAILED
    }

    // ✅ Default constructor
    public Orders() {
        this.status = OrderStatus.PENDING;
        this.paymentStatus = PaymentStatus.PENDING;
    }

    // ✅ Getters & Setters

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public String getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public void setRazorpayPaymentId(String razorpayPaymentId) {
        this.razorpayPaymentId = razorpayPaymentId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
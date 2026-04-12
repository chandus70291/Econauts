package com.econauts.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.econauts.Entity.Orders;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByEmail(String email);
    
    Optional<Orders> findByRazorpayOrderId(String razorpayOrderId);
    
    @Query("SELECT SUM(o.totalAmount) FROM Orders o WHERE o.paymentStatus = 'SUCCESS'")
    Double getTotalRevenue();
    
    @Query("SELECT COUNT(o) FROM Orders o")
    Long getTotalOrders();

    List<Orders> findTop5ByOrderByCreatedAtDesc();
}
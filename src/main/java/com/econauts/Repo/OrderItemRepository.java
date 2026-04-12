package com.econauts.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.econauts.Entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
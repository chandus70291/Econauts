package com.econauts.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.econauts.Entity.Orders;
import com.econauts.Service.OrderService;
import com.econauts.dto.ApiResponse;
import com.econauts.dto.OrderResponse;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // ✅ PLACE ORDER
    @PostMapping
    public ApiResponse<OrderResponse> placeOrder() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Orders order = orderService.placeOrder(email);

        OrderResponse response = new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus().toString()
        );

        return new ApiResponse<>(true, "Order placed", response);
    }

    // ✅ USER ORDERS
    @GetMapping
    public ApiResponse<List<Orders>> getOrders() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        List<Orders> orders = orderService.getUserOrders(email);

        return new ApiResponse<List<Orders>>(
                true,
                "Orders fetched",
                orders
        );
    }

    // ✅ ADMIN: GET ALL ORDERS
    @GetMapping("/admin/all")
    public ApiResponse<List<Orders>> getAllOrders() {

        List<Orders> orders = orderService.getAllOrders();

        return new ApiResponse<List<Orders>>(
                true,
                "All orders fetched",
                orders
        );
    }

    // ✅ ADMIN: TOTAL REVENUE
    @GetMapping("/admin/revenue")
    public ApiResponse<Double> getRevenue() {

        double revenue = orderService.getTotalRevenue();

        return new ApiResponse<Double>(
                true,
                "Total revenue fetched",
                revenue
        );
    }

    // ✅ ADMIN: UPDATE ORDER STATUS (ONLY ONE METHOD)
    @PutMapping("/admin/{orderId}/status")
    public ApiResponse<String> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam Orders.OrderStatus status) {

        orderService.updateOrderStatus(orderId, status);

        return new ApiResponse<String>(
                true,
                "Order status updated",
                status.name()
        );
    }
}
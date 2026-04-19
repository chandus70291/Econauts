package com.econauts.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.econauts.Entity.*;
import com.econauts.Repo.*;

@Service
public class OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    
    private String razorpayOrderId;
    
    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    @Transactional
    public Orders placeOrder(String email) {

        List<Cart> cartItems = cartRepository.findByEmail(email);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Orders order = new Orders();
        order.setEmail(email);

        order = orderRepository.save(order);

        double total = 0;

        for (Cart item : cartItems) {

            Product product = item.getProduct(); // ✅ FIXED

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setOrder(order);

            total += product.getPrice() * item.getQuantity();

            orderItemRepository.save(orderItem);
        }

        order.setTotalAmount(total);

        // ✅ Payment pending initially
        order.setPaymentStatus(Orders.PaymentStatus.PENDING);

        orderRepository.save(order);

        // ✅ Clear cart
        cartRepository.deleteAll(cartItems);

        return order;
    }

    public List<Orders> getUserOrders(String email) {
        return orderRepository.findByEmail(email);
    }
    
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public double getTotalRevenue() {
        Double revenue = orderRepository.getTotalRevenue();
        return revenue != null ? revenue : 0;
    }

    // ✅ ADMIN: Update order status
    public void updateOrderStatus(Long orderId, Orders.OrderStatus status) {

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);

        orderRepository.save(order);
    }

    // ✅ PAYMENT SUCCESS (after Razorpay)
    public void markPaymentSuccess(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setPaymentStatus(Orders.PaymentStatus.SUCCESS);
        order.setStatus(Orders.OrderStatus.CONFIRMED);

        orderRepository.save(order);
    }
}
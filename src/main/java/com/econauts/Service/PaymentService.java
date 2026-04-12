package com.econauts.Service;

import com.econauts.Entity.Orders;
import com.econauts.Entity.Orders.PaymentStatus;
import com.econauts.Repo.OrderRepository;
import com.razorpay.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private RazorpayClient razorpayClient;

    @Autowired
    private OrderRepository orderRepository;

    public String createPaymentOrder(Orders order) throws Exception {

        JSONObject options = new JSONObject();
        options.put("amount", (int)(order.getTotalAmount() * 100));
        options.put("currency", "INR");
        options.put("receipt", "order_" + order.getId());

        Order razorpayOrder = razorpayClient.orders.create(options);

        // Save razorpay order id
        order.setPaymentStatus(PaymentStatus.PENDING);
        orderRepository.save(order);

        return razorpayOrder.toString();
    }
}
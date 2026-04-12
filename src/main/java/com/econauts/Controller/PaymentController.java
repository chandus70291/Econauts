package com.econauts.Controller;

import com.econauts.Entity.Orders;
import com.econauts.Repo.OrderRepository;
import com.econauts.dto.ApiResponse;
import com.razorpay.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private RazorpayClient razorpayClient;
    
    @Autowired
    private OrderRepository orderRepository;

    @Value("${razorpay.secret}")
    private String razorpaySecret;

    // ✅ CREATE ORDER
    @PostMapping("/create-order")
    public ApiResponse<Map<String, Object>> createOrder(@RequestParam double amount) throws Exception {

        if (amount <= 0) {
            return new ApiResponse<>(false, "Invalid amount", null);
        }

        // ✅ Create Razorpay order
        JSONObject options = new JSONObject();
        options.put("amount", (int) (amount * 100)); // in paise
        options.put("currency", "INR");
        options.put("receipt", "txn_" + System.currentTimeMillis());

        Order razorpayOrder = razorpayClient.orders.create(options);

        // ✅ SAVE IN DB (THIS WAS MISSING)
        Orders dbOrder = new Orders();
        dbOrder.setTotalAmount(amount);
        dbOrder.setPaymentStatus(Orders.PaymentStatus.PENDING);
        dbOrder.setRazorpayOrderId(razorpayOrder.get("id").toString());

        orderRepository.save(dbOrder);

        // ✅ Response to frontend
        Map<String, Object> response = new HashMap<>();
        response.put("orderId", razorpayOrder.get("id"));
        response.put("amount", razorpayOrder.get("amount"));
        response.put("currency", razorpayOrder.get("currency"));

        return new ApiResponse<>(true, "Order created", response);
    }

    // ✅ VERIFY PAYMENT
    @PostMapping("/verify")
    public ApiResponse<String> verifyPayment(@RequestBody Map<String, String> data) {

        String orderId = data.get("razorpay_order_id");
        String paymentId = data.get("razorpay_payment_id");
        String signature = data.get("razorpay_signature");

        try {
            String payload = orderId + "|" + paymentId;
            String expectedSignature = Utils.getHash(payload, razorpaySecret);

            if (!expectedSignature.equals(signature)) {
                return new ApiResponse<>(false, "Invalid signature", null);
            }

            // ✅ UPDATE DB HERE
            Orders order = orderRepository
                    .findByRazorpayOrderId(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            order.setPaymentStatus(Orders.PaymentStatus.SUCCESS);
            orderRepository.save(order);

            return new ApiResponse<>(true, "Payment verified", "SUCCESS");

        } catch (Exception e) {
            return new ApiResponse<>(false, "Verification failed", e.getMessage());
        }
    }
}
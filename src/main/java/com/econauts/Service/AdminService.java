package com.econauts.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.econauts.Repo.OrderRepository;
import com.econauts.Repo.UserRepository;
import com.econauts.dto.DashboardResponse;

@Service
public class AdminService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public DashboardResponse getDashboard() {

        long totalUsers = userRepository.getTotalUsers();
        long totalOrders = orderRepository.getTotalOrders();

        Double revenue = orderRepository.getTotalRevenue();
        double totalRevenue = revenue != null ? revenue : 0;

        var recentOrders = orderRepository.findTop5ByOrderByCreatedAtDesc();

        return new DashboardResponse(
                totalUsers,
                totalOrders,
                totalRevenue,
                recentOrders
        );
    }
}
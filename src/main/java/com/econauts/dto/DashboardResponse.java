package com.econauts.dto;

import java.util.List;
import com.econauts.Entity.Orders;

public class DashboardResponse {

    private long totalUsers;
    private long totalOrders;
    private double totalRevenue;
    private List<Orders> recentOrders;

    public DashboardResponse(long totalUsers, long totalOrders, double totalRevenue, List<Orders> recentOrders) {
        this.totalUsers = totalUsers;
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
        this.recentOrders = recentOrders;
    }

    public long getTotalUsers() { return totalUsers; }
    public long getTotalOrders() { return totalOrders; }
    public double getTotalRevenue() { return totalRevenue; }
    public List<Orders> getRecentOrders() { return recentOrders; }
}
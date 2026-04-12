package com.econauts.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.econauts.Service.AdminService;
import com.econauts.dto.ApiResponse;
import com.econauts.dto.DashboardResponse;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public ApiResponse<DashboardResponse> getDashboard() {

        DashboardResponse data = adminService.getDashboard();

        return new ApiResponse<>(true, "Dashboard data fetched", data);
    }
}
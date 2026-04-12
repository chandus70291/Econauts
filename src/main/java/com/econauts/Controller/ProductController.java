package com.econauts.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.econauts.Entity.Product;
import com.econauts.Service.ProductService;
import com.econauts.dto.ApiResponse;

@RestController
@RequestMapping("/api/products") // 🌍 PUBLIC
public class ProductController {

    @Autowired
    private ProductService productService;

    // ✅ GET ALL PRODUCTS (pagination)
    @GetMapping
    public ApiResponse<Page<Product>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return new ApiResponse<>(
                true,
                "Products fetched",
                productService.getProducts(page, size)
        );
    }

    // ✅ GET PRODUCT BY ID
    @GetMapping("/{id}")
    public ApiResponse<Product> getProduct(@PathVariable Long id) {
        return new ApiResponse<>(
                true,
                "Product fetched",
                productService.getProduct(id)
        );
    }

    // ✅ SEARCH WITH PAGINATION
    @GetMapping("/search")
    public ApiResponse<Page<Product>> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return new ApiResponse<>(
                true,
                "Search results",
                productService.searchProducts(keyword, page, size)
        );
    }
}
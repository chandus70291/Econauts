package com.econauts.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.econauts.Entity.Product;
import com.econauts.Service.ProductService;
import com.econauts.dto.ApiResponse;

@RestController
@RequestMapping("/admin/products") // ADMIN ONLY
public class AdminProductController {

    @Autowired
    private ProductService productService;

    // ADD PRODUCT
    @PostMapping
    public ApiResponse<Product> addProduct(@RequestBody Product product) {
        return new ApiResponse<>(true, "Product added",
                productService.addProduct(product));
    }

    // UPDATE PRODUCT
    @PutMapping("/{id}")
    public ApiResponse<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {

        return new ApiResponse<>(true, "Product updated",
                productService.updateProduct(id, product));
    }

    // DELETE PRODUCT
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ApiResponse<>(true, "Product deleted", null);
    }
}
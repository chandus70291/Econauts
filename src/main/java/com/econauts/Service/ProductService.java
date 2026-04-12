package com.econauts.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.econauts.Entity.Product;
import com.econauts.Repo.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // ✅ ADD PRODUCT
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // ✅ GET PRODUCTS WITH PAGINATION (REPLACE getAllProducts)
    public Page<Product> getProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

    // ✅ GET PRODUCT BY ID
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // ✅ SEARCH
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    // ✅ UPDATE PRODUCT
    public Product updateProduct(Long id, Product updated) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(updated.getName());
        product.setDescription(updated.getDescription());
        product.setPrice(updated.getPrice());
        product.setStock(updated.getStock());
        product.setImageUrl(updated.getImageUrl());

        return productRepository.save(product);
    }
    
    public Page<Product> searchProducts(String keyword, int page, int size) {
        return productRepository.findByNameContainingIgnoreCase(
                keyword,
                PageRequest.of(page, size)
        );
    }

    // ✅ DELETE PRODUCT
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
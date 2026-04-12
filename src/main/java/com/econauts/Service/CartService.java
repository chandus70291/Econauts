package com.econauts.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.econauts.Entity.Cart;
import com.econauts.Entity.Product;
import com.econauts.Repo.CartRepository;
import com.econauts.Repo.ProductRepository;
import com.econauts.dto.CartResponse;
import com.econauts.dto.CartSummary;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    // ✅ ADD / UPDATE CART
    public Cart addToCart(String email, Long productId, int quantity) {

        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Optional: stock check
        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock");
        }

        Cart existing = cartRepository
                .findByEmailAndProduct_Id(email, productId);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            return cartRepository.save(existing);
        }

        Cart cart = new Cart();
        cart.setEmail(email);
        cart.setProduct(product);
        cart.setQuantity(quantity);

        return cartRepository.save(cart);
    }

    // ✅ GET CART
    public CartSummary getCart(String email) {

        List<Cart> cartItems = cartRepository.findByEmail(email);

        List<CartResponse> response = new ArrayList<>();

        for (Cart item : cartItems) {
            Product product = item.getProduct();

            response.add(new CartResponse(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    item.getQuantity()
            ));
        }

        double grandTotal = response.stream()
                .mapToDouble(CartResponse::getTotal)
                .sum();

        return new CartSummary(response, grandTotal);
    }

    // ✅ REMOVE ITEM
    public void removeItem(Long id, String email) {

        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        // 🔐 Security check
        if (!cart.getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        cartRepository.delete(cart);
    }
}
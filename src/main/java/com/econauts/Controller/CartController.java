package com.econauts.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.econauts.Entity.Cart;
import com.econauts.Service.CartService;
import com.econauts.dto.ApiResponse;
import com.econauts.dto.CartSummary;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // ✅ ADD TO CART
    @PostMapping
    public ApiResponse<Cart> addToCart(
            @RequestParam Long productId,
            @RequestParam int quantity) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Cart cart = cartService.addToCart(email, productId, quantity);

        return new ApiResponse<>(true, "Added to cart", cart);
    }

    // ✅ GET CART
    @GetMapping
    public ApiResponse<CartSummary> getCart() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return new ApiResponse<>(
                true,
                "Cart fetched",
                cartService.getCart(email)
        );
    }

    // ✅ REMOVE ITEM
    @DeleteMapping("/{id}")
    public ApiResponse<String> removeItem(@PathVariable Long id) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        cartService.removeItem(id, email);

        return new ApiResponse<>(true, "Item removed", null);
    }
}
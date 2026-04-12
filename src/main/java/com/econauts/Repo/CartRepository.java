package com.econauts.Repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.econauts.Entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByEmail(String email);

    Cart findByEmailAndProduct_Id(String email, Long productId);
}


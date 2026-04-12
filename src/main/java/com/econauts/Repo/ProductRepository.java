package com.econauts.Repo;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import com.econauts.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	List<Product> findByNameContainingIgnoreCase(String name);
	Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
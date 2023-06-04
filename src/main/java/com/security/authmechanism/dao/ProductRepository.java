package com.security.authmechanism.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.authmechanism.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}


package com.security.authmechanism.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.authmechanism.entity.Product;
import com.security.authmechanism.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
public class PublicController {
	 private final ProductService productService;
	    
	    public PublicController(ProductService productService) {
	        this.productService = productService;
	    }
	    
	    @GetMapping
	    public ResponseEntity<List<Product>> getAllProducts() {
	        List<Product> products = productService.getAllProducts();
	        return ResponseEntity.ok(products);
	    }
}

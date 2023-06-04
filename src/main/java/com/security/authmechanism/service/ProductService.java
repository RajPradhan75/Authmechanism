package com.security.authmechanism.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.security.authmechanism.dao.ProductRepository;
import com.security.authmechanism.dao.Role;
import com.security.authmechanism.dto.ProductRequest;
import com.security.authmechanism.entity.Product;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Optional<Product> getProductById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()) {
            boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(Role.ADMIN.name()));

            if (isAdmin || authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(Role.USER.name()))) {
                return productRepository.findById(id);
            } else {
               
                throw new AccessDeniedException("You are not authorized to access this resource");
            }
        } else {
           
            throw new AccessDeniedException("You are not authorized to access this resource");
        }
    }

	public Product createProduct(ProductRequest request) {
		boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals(Role.ADMIN.name()));

		if (!isAdmin) {
			throw new AccessDeniedException("Access is denied. User does not have the required role.");
		}
		Product product = new Product(request.getName(), request.getDescription());
		return productRepository.save(product);
	}

	  public Optional<Product> updateProduct(Long id, ProductRequest request) {
	        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
	                .anyMatch(authority -> authority.getAuthority().equals(Role.ADMIN.name()));

	        if (!isAdmin) {
	            throw new AccessDeniedException("Access is denied. User does not have the required role.");
	        }

	        Optional<Product> optionalProduct = productRepository.findById(id);
	        if (optionalProduct.isPresent()) {
	            Product product = optionalProduct.get();
	            product.setName(request.getName());
	            product.setDescription(request.getDescription());
	            return Optional.of(productRepository.save(product));
	        } else {
	            return Optional.empty();
	        }
	    }

	  public boolean deleteProduct(Long id) {
	        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
	                .anyMatch(authority -> authority.getAuthority().equals(Role.ADMIN.name()));

	        if (!isAdmin) {
	            throw new AccessDeniedException("Access is denied. User does not have the required role.");
	        }

	        Optional<Product> optionalProduct = productRepository.findById(id);
	        if (optionalProduct.isPresent()) {
	            productRepository.delete(optionalProduct.get());
	            return true;
	        } else {
	            return false;
	        }
	    }

}
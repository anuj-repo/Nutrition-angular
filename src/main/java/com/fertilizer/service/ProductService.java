package com.fertilizer.service;

import java.util.List;
import java.util.Optional;

import com.fertilizer.model.Product;

public interface ProductService {

	public List<Product> getAllProducts();

	public Optional<Product> getProductById(Long id);

	public Product addProduct(Product product);

	public void deleteProduct(Long id);

}
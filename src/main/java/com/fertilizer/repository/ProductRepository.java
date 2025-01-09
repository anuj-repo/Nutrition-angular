package com.fertilizer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fertilizer.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>{

	public List<Product> findAll();
	
	public List<Product>  findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
			String key1, String key2);
	

}

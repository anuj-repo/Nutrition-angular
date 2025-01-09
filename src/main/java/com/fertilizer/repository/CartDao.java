package com.fertilizer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fertilizer.model.Cart;
import com.fertilizer.model.User;

@Repository
public interface CartDao extends CrudRepository<Cart, Integer>{
	
	public List<Cart> findByUser(User user);

}

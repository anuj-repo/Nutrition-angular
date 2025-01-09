package com.fertilizer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fertilizer.model.OrderDetail;
import com.fertilizer.model.Product;
import com.fertilizer.model.User;

@Repository
public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer>{
	
	List<OrderDetail> findByUser(User user);
	
	List<OrderDetail> findAll();

}
	
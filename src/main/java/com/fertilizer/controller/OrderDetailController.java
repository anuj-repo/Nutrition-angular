package com.fertilizer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fertilizer.model.OrderDetail;
import com.fertilizer.model.OrderInput;
import com.fertilizer.payload.response.OrderDetailsResponse;
import com.fertilizer.service.impl.OrderDetailService;

@RestController
public class OrderDetailController {

	@Autowired
	private OrderDetailService orderDetailService;

	@PreAuthorize("hasRole('User')")
	@PostMapping({ "/placeOrder/{isSingleProductCheckout}" })
	public void placeOrder(@PathVariable(name = "isSingleProductCheckout") boolean isSingleProductCheckout,
			@RequestBody OrderInput orderInput) {
		orderDetailService.placeOrder(orderInput, isSingleProductCheckout);

	}

	@PreAuthorize("hasRole('User')")
	@GetMapping({ "/getOrderDetails" })
	public List<OrderDetailsResponse> getOrderDetails() {
		return orderDetailService.getOrderDetails();
	}

	@PreAuthorize("hasRole('Admin')")
	@GetMapping({ "/getAllOrderDetails" })
	public List<OrderDetailsResponse> getAllOrderDetails() {
		return orderDetailService.getAllOrderDetails();
	}

}

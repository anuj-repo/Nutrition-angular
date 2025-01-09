package com.fertilizer.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fertilizer.model.Cart;
import com.fertilizer.model.OrderDetail;
import com.fertilizer.model.OrderInput;
import com.fertilizer.model.OrderProductQuantity;
import com.fertilizer.model.Product;
import com.fertilizer.model.User;
import com.fertilizer.payload.response.OrderDetailsResponse;
import com.fertilizer.repository.CartDao;
import com.fertilizer.repository.OrderDetailRepository;
import com.fertilizer.repository.ProductRepository;
import com.fertilizer.service.UserService;

@Service
public class OrderDetailService {

	private static final String ORDER_PLACED = "Placed";

	@Autowired
	private OrderDetailRepository orderDetailDao;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartDao cartDao;

	@Autowired
	private UserService userService;

	@Transactional
	public List<OrderDetailsResponse> getAllOrderDetails() {
		// List<OrderDetail> orderDetails = new ArrayList<>();
		// orderDetailDao.findAll().forEach(e -> orderDetails.add(e));
		List<OrderDetail> orderDetails = orderDetailDao.findAll();

		List<OrderDetailsResponse> order = new ArrayList<>();

		orderDetails.forEach(e -> order.add(new OrderDetailsResponse(e.getOrderId(), e.getProduct().getProductName(),
				e.getOrderFullName(), e.getOrderFullName(), e.getOrderContactNumber(), e.getOrderStatus(),e.getOrderAmount())));

		return order;
	}

	@Transactional
	public List<OrderDetailsResponse> getOrderDetails() {
		User user = userService.getSignedInUserDTO();

		List<OrderDetail> orderDetails=orderDetailDao.findByUser(user);
		List<OrderDetailsResponse> order = new ArrayList<>();

		orderDetails.forEach(e -> order.add(new OrderDetailsResponse(e.getOrderId(), e.getProduct().getProductName(),
				e.getOrderFullName(), e.getOrderFullName(), e.getOrderContactNumber(), e.getOrderStatus(),e.getOrderAmount())));
		return order;
	}

	public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout) {
		List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();

		for (OrderProductQuantity o : productQuantityList) {
			Product product = productRepository.findById(o.getProductId()).get();

			User user = userService.getSignedInUserDTO();

			OrderDetail orderDetail = new OrderDetail(orderInput.getFullName(), orderInput.getFullAddress(),
					orderInput.getContactNumber(), orderInput.getAlternateContactNumber(), ORDER_PLACED,
					product.getProductDiscountedPrice() * o.getQuantity(), product, user);

			if (!isSingleProductCheckout) {
				List<Cart> carts = cartDao.findByUser(user);
				carts.stream().forEach(x -> cartDao.deleteById(x.getCartId()));

			}
			orderDetailDao.save(orderDetail);
		}
	}

}

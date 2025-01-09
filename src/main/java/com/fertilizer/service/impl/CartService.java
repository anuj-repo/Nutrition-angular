package com.fertilizer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fertilizer.model.Cart;
import com.fertilizer.model.Product;
import com.fertilizer.model.User;
import com.fertilizer.payload.response.CardDetailsResponse;
import com.fertilizer.payload.response.OrderDetailsResponse;
import com.fertilizer.repository.CartDao;
import com.fertilizer.repository.ProductRepository;
import com.fertilizer.repository.UserRepository;
import com.fertilizer.service.UserService;

@Service
public class CartService {

	@Autowired
	private CartDao cartDao;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	public void deleteCartItem(Integer cartId) {
		cartDao.deleteById(cartId);
	}

	public Cart addToCart(Integer productId) {

		Product product = productRepository.findById(productId).get();

		User user = userService.getSignedInUserDTO();

		List<Cart> cartList = cartDao.findByUser(user);
		List<Cart> filteredList = cartList.stream().filter(x -> x.getProduct().getProductId() == productId)
				.collect(Collectors.toList());

		if (filteredList.size() > 0) {
			return null;
		}

		if (product != null && user != null) {
			Cart cart = new Cart(product, user);
			return cartDao.save(cart);
		}
		return null;
	}

	public List<CardDetailsResponse> getCartDetails() {
		User user = userService.getSignedInUserDTO();
		List<Cart> card = cartDao.findByUser(user);

		List<CardDetailsResponse> cartResponse = new ArrayList<>();
		
		card.forEach(e -> cartResponse.add(new CardDetailsResponse(e.getCartId(), e.getProduct().getProductName(),
				e.getProduct().getProductDescription(), e.getProduct().getProductActualPrice(),
				e.getProduct().getProductDiscountedPrice())));

		return cartResponse;

	}

}

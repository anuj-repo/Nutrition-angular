package com.fertilizer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fertilizer.dao.UserDao;
import com.fertilizer.model.Cart;
import com.fertilizer.model.Product;
import com.fertilizer.model.User;
import com.fertilizer.repository.CartDao;
import com.fertilizer.repository.ProductRepository;
import com.fertilizer.service.UserService;

@Service
public class ProductServiceImpl {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserDao userDao;
	@Autowired
	private CartDao cartDao;

	@Autowired
	private UserService userService;

	public Product addNewProduct(Product product) {
		return productRepository.save(product);
	}

	public List<Product> getAllProducts(int pageNumber, String searchKey) {
		// Pageable pageable = PageRequest.of(pageNumber, 8);

		if (searchKey.equals("")) {
			List<Product> product = productRepository.findAll();
			return product;
		} else {

			List<Product> product = (List<Product>) productRepository
					.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(searchKey,
							searchKey);
			return product;
		}

	}

	public void deleteProductDetails(Integer productId) {
		productRepository.deleteById(productId);
	}

	public Product getProductDetailsById(Integer productId) {

		return productRepository.findById(productId).get();
	}

	public List<Product> getProductDetails(boolean isSingeProductCheckout, Integer productId) {

		if (isSingeProductCheckout && productId != 0) {
			List<Product> list = new ArrayList<>();
			Product product = productRepository.findById(productId).get();
			list.add(product);
			return list;
		} else {

			User user = userService.getSignedInUserDTO();
			List<Cart> carts = cartDao.findByUser(user);

			return carts.stream().map(x -> x.getProduct()).collect(Collectors.toList());

		}

	}

}

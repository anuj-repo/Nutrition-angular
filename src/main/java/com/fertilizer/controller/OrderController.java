package com.fertilizer.controller;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fertilizer.model.Product;
import com.fertilizer.service.OrderService;
import com.fertilizer.service.UserService;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {

	private static final Logger logger = LogManager.getLogger(OrderController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private OrderService orderService;

	private ModelMapper modelMapper = new ModelMapper();

	@PostConstruct
	void init() {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	

}
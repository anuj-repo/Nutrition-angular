package com.fertilizer.service.impl;

import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fertilizer.repository.UserRepository;
import com.fertilizer.service.AdClientsDataService;
import com.fertilizer.service.ClientService;

@Service
public class AdClientsDataServiceImpl implements AdClientsDataService {

	private static final Logger logger = LogManager.getLogger(AdClientsDataServiceImpl.class);

	private Predicate<Object> nullCheckPredicate;

	private ModelMapper modelMapper = new ModelMapper();


	@Autowired
	public ClientService clientService;

	@Autowired
	public UserRepository userRepository;

	

	private static final String NEED_UPDATE = "Account ID to be updated in Adworks";


	@PostConstruct
	void init() {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		nullCheckPredicate = Objects::nonNull;
	}

	

	

  
}

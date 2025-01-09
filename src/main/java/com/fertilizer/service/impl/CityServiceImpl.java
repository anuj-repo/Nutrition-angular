package com.fertilizer.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fertilizer.dao.CityDao;
import com.fertilizer.model.City;
import com.fertilizer.model.Country;
import com.fertilizer.model.State;
import com.fertilizer.repository.CityRepository;
import com.fertilizer.repository.CountryRepository;
import com.fertilizer.repository.StateRepository;
import com.fertilizer.request.CountryListRequestDTO;
import com.fertilizer.request1.CitySearchDto;
import com.fertilizer.request1.CitySearchRequest;
import com.fertilizer.request1.StateSearchDto;
import com.fertilizer.service.CityService;
import com.fertilizer.util.Response;

@Service
public class CityServiceImpl implements CityService {

	private static final Logger logger = LogManager.getLogger(CityServiceImpl.class);

	@Autowired
	private CityDao cityDao;

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	CityRepository cityRepository;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<Response<List<City>>> getCities(CitySearchDto citySearchDto) {

		logger.debug("CityServiceImpl Service getCities method Called");

		return ResponseEntity.ok(new Response<>(cityDao.getCity(citySearchDto), cityDao.count(citySearchDto),
				"List Fetched Successfully."));
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<Response<List<State>>> getState(StateSearchDto stateSearchDto) {
		logger.debug("CityServiceImpl Service getState method Called");

		return ResponseEntity.ok(new Response<>(cityDao.getState(stateSearchDto), cityDao.countState(stateSearchDto),
				"List Fetched Successfully."));
	}

	@Override
	public ResponseEntity<Response<List<Country>>> getCountry(CountryListRequestDTO countrtListRequestDTO) {
		List<Country> country = countryRepository.findAll();
		return ResponseEntity.ok(new Response<>(country, null, "List Fetched Successfully."));
	}

	@Override
	public ResponseEntity<Response<List<City>>> getCitiesByStateIdAndCityId(CitySearchRequest citySearchRequest) {
		return ResponseEntity.ok(new Response<>(cityRepository.findAllBycountryIdAndStateId(citySearchRequest.getCountryId(),citySearchRequest.getStateId()), null, "List Fetched Successfully."));
		

	}

}

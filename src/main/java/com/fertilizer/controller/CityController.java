package com.fertilizer.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fertilizer.model.City;
import com.fertilizer.model.Country;
import com.fertilizer.model.State;
import com.fertilizer.request.CountryListRequestDTO;
import com.fertilizer.request1.CityListRequestDTO;
import com.fertilizer.request1.CitySearchDto;
import com.fertilizer.request1.CitySearchRequest;
import com.fertilizer.request1.StateListRequestDTO;
import com.fertilizer.request1.StateSearchDto;
import com.fertilizer.service.CityService;
import com.fertilizer.util.Response;

@RestController
@CrossOrigin
public class CityController {

	private static final Logger logger = LogManager.getLogger(CityController.class);

	@Autowired
	private CityService cityService;
	
	
	@PostMapping("/city")
	public ResponseEntity<Response<List<City>>> getAllCity(@RequestBody CitySearchRequest CitySearchRequest) {

		
		return cityService.getCitiesByStateIdAndCityId(CitySearchRequest);
	}

	

	@PostMapping("/city1")
	public ResponseEntity<Response<List<City>>> getAllCity(@RequestBody CityListRequestDTO cityListRequestDto) {

		logger.debug("CityController Controller getAllCity method Called");

		if (cityListRequestDto.getStatus() == null) {
			cityListRequestDto.setStatus("1");
		}
		CitySearchDto citySearchDto = new CitySearchDto(cityListRequestDto);
		return cityService.getCities(citySearchDto);
	}

	@GetMapping("/state")
	public ResponseEntity<Response<List<State>>> getAllState(@Valid StateListRequestDTO stateListRequestDTO) {

		logger.debug("CityController Controller getAllState method Called");

		if (stateListRequestDTO.getStatus() == null) {
			stateListRequestDTO.setStatus("1");
		}

		StateSearchDto stateSearchDto = new StateSearchDto(stateListRequestDTO);

		return cityService.getState(stateSearchDto);
	}

	@GetMapping("/country")
	public ResponseEntity<Response<List<Country>>> getAllCountry(CountryListRequestDTO countrtListRequestDTO) {
		logger.debug("Country Controller method Called");
		return cityService.getCountry(countrtListRequestDTO);
	}

//	@GetMapping("/city")
//	public ResponseEntity<List<City>> getCities(@RequestParam String stateId, @RequestParam String countryId) {
//		List<City> cities = cityService.getCitiesByStateAndCountry(Long.parseLong(stateId), Long.parseLong(countryId));
//		return ResponseEntity.ok(cities);
//	}
//
//	@GetMapping("/state")
//	public ResponseEntity<List<State>> getState(@RequestParam String countryId) {
//
//		List<State> cities = cityService.getStateByCountryID(Long.parseLong(countryId));
//		return ResponseEntity.ok(cities);
//	}

}

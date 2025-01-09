package com.fertilizer.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.fertilizer.model.City;
import com.fertilizer.model.Country;
import com.fertilizer.model.State;
import com.fertilizer.request.CountryListRequestDTO;
import com.fertilizer.request1.CitySearchDto;
import com.fertilizer.request1.CitySearchRequest;
import com.fertilizer.request1.StateSearchDto;
import com.fertilizer.util.Response;

public interface CityService {

	ResponseEntity<Response<List<City>>> getCities(CitySearchDto citySearchDto);

	ResponseEntity<Response<List<State>>> getState(StateSearchDto stateSearchDto);

	ResponseEntity<Response<List<Country>>> getCountry(CountryListRequestDTO countrtListRequestDTO);

	ResponseEntity<Response<List<City>>> getCitiesByStateIdAndCityId(CitySearchRequest citySearchRequest);

}

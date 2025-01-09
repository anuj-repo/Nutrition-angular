package com.fertilizer.dao;

import java.util.List;

import com.fertilizer.model.City;
import com.fertilizer.model.State;
import com.fertilizer.request1.CitySearchDto;
import com.fertilizer.request1.StateSearchDto;

public interface CityDao {

	List<City> getCity(CitySearchDto citySearchDto);

	Long count(CitySearchDto citySearchDto);

	List<State> getState(StateSearchDto stateSearchDto);

	Long countState(StateSearchDto stateSearchDto);

}

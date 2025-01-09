package com.fertilizer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fertilizer.enums.BooleanEnum;
import com.fertilizer.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

	List<City> findAllByOrderByCityNameAsc();

	Optional<City> findByCityName(String cityname);

	List<City> findAllBycountryIdAndStateId(Long countryId, Long stateId);

}

package com.fertilizer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fertilizer.model.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

	Optional<State> findByStateName(String stateName);

	List<State> findByCountryId(Long countryId);
}

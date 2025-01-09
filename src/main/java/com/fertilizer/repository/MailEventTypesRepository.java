package com.fertilizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fertilizer.model.MailEventTypes;

public interface MailEventTypesRepository extends JpaRepository<MailEventTypes, Long>{
	
	MailEventTypes findByEventIdentifier(String eventIdentifier);
}

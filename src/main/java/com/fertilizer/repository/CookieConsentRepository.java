package com.fertilizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fertilizer.model.CookieConsent;


@Repository
public interface CookieConsentRepository extends JpaRepository<CookieConsent, Long> {
	
}

package com.fertilizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fertilizer.model.PaymentDetailsRegistration;

@Repository
public interface PaymentDetailsRegistrationRepository extends JpaRepository<PaymentDetailsRegistration, Long> {

}

package com.fertilizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fertilizer.model.OrderPayment;

import java.lang.Long;
import java.util.Optional;

@Repository
public interface OrderPaymentRepository extends JpaRepository<OrderPayment, Long> {

	Optional<OrderPayment> findByOrderId(Long orderid);

	Optional<OrderPayment> findByIdAndUserId(Long id, Long userId);

	Optional<OrderPayment> findById(Long id);

	Optional<OrderPayment> findByMerchantReferenceNo(String merchantRefrenceNo);

	Optional<OrderPayment> findByOrderPackagesIdAndUserId(String OrderPackagesId, Long userId);
	
	Optional<OrderPayment> findByOrderIdAndUserId(Long orderId, Long userId);

}

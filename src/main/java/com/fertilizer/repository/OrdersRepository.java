package com.fertilizer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fertilizer.enums.Status;
import com.fertilizer.model.Orders;
import com.fertilizer.model.User;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
	Optional<Orders> findByIdAndUser(Long orderid, User user);

	Optional<Orders> findByInternalOrderNumber(String internalOrderNumber);

	List<Orders> findByUserIdAndStatus(Long userId, Status status);

	List<Orders> findByUpdatedByAndStatus(Long userId, Status active);
}

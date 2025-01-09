package com.fertilizer.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fertilizer.model.ApplicationLog;


@Repository
public interface ApplicationLogRepository extends JpaRepository<ApplicationLog, Long> {
	@Modifying
	@Transactional
	@Query("DELETE FROM ApplicationLog where requestDate=?1")
	void deleteRequestResponseForDay(Date requestDate);
}

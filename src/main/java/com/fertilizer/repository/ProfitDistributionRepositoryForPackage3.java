package com.fertilizer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fertilizer.model.ProfitDistributionMasterPackage3;

@Repository
public interface ProfitDistributionRepositoryForPackage3 extends JpaRepository<ProfitDistributionMasterPackage3, Long> {
	
	List<ProfitDistributionMasterPackage3> findAll();
}

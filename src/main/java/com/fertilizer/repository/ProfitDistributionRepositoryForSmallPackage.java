package com.fertilizer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fertilizer.model.ProfitDistributionMasterSmallPackage;

@Repository
public interface ProfitDistributionRepositoryForSmallPackage extends JpaRepository<ProfitDistributionMasterSmallPackage, Long> {
	
	List<ProfitDistributionMasterSmallPackage> findAll();
	
}

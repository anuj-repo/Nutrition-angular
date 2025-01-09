package com.fertilizer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fertilizer.enums.Status;
import com.fertilizer.model.Query;

/**
 * @author kabir
 *
 */
@Repository
public interface QueryRepository extends JpaRepository<Query, Long> {

	List<Query> findByIdInAndStatusNot(List<Long> idsToDelete, Status status);


}

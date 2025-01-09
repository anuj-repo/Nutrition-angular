package com.fertilizer.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fertilizer.dao.UserActivityLogsDao;

@Repository
public class UserActivityLogsDaoImpl implements UserActivityLogsDao {
	
	@Autowired
	private EntityManager entityManager;

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@Override
	public void updateUserActivityLog(List<Long> id, String response) {
		entityManager.createQuery("UPDATE UserActivityLogs set response= :response where id in :id")
		.setParameter("id", id)
		.setParameter("response", response).executeUpdate();

	}

}

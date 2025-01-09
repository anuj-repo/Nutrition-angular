package com.fertilizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fertilizer.model.UserClients;

@Repository
public interface UserClientsRepository extends JpaRepository<UserClients, Long> {

}

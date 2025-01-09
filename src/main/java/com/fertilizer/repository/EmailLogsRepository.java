package com.fertilizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fertilizer.model.EmailLogs;

public interface EmailLogsRepository extends JpaRepository<EmailLogs, Long> {

}

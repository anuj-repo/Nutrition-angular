package com.fertilizer.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.fertilizer.model.User;
import com.fertilizer.request.RegistrationPaymentRequest;
import com.fertilizer.util.Response;

public interface CommissionDistributionServiceForPackage1 {

	void distributeRegistrationFee(User user);

	void reRegisterUser();

	ResponseEntity<Response<String>> updateRegistrationPaymentStatus(RegistrationPaymentRequest registration,
			MultipartFile[] file);

}

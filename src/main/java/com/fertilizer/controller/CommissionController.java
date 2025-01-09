package com.fertilizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fertilizer.request.RegistrationPaymentRequest;
import com.fertilizer.service.impl.CommissionDistributionServiceForPackage1ServiceImpl;
import com.fertilizer.util.Response;

@RestController
public class CommissionController {
//
//	@Autowired
//	CommissionDistributionServiceForPackage1 commissionDistributionService;
	
	@Autowired
	CommissionDistributionServiceForPackage1ServiceImpl commissionDistributionServiceForPackage1ServiceImpl;
	
	

	// @PreAuthorize("hasRole('Admin')")
	@PostMapping(value = { "/uploadPaymentReceipt" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Response<String>> uploadPaymentReceipt(
			@RequestPart("registrationPaymentRequest") RegistrationPaymentRequest registration,
			@RequestPart("imageFile") MultipartFile[] file) {
		try {
			ResponseEntity<Response<String>> result = commissionDistributionServiceForPackage1ServiceImpl
					.updateRegistrationPaymentStatus(registration,file);
			return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	

}

package com.fertilizer.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fertilizer.enums.UserLevel;
import com.fertilizer.model.PaymentDetailsRegistration;
import com.fertilizer.model.ProfitDistributionMasterSmallPackage;
import com.fertilizer.model.ReEntryProfitDistribution;
import com.fertilizer.model.RegistrationPaymentImage;
import com.fertilizer.model.User;
import com.fertilizer.repository.PaymentDetailsRegistrationRepository;
import com.fertilizer.repository.ProfitDistributionRepositoryForSmallPackage;
import com.fertilizer.repository.ReEntryProfitDistributionRepository;
import com.fertilizer.repository.UserRepository;
import com.fertilizer.request.RegistrationPaymentRequest;
import com.fertilizer.service.CommissionDistributionServiceForPackage1;
import com.fertilizer.util.Response;

//@Service("commissionDistributionServiceForPackage2ServiceImpl")
@Service
public class CommissionDistributionServiceForPackage2ServiceImpl implements CommissionDistributionServiceForPackage1 {

	private static final Logger log = LoggerFactory
			.getLogger(CommissionDistributionServiceForPackage2ServiceImpl.class);

	@Autowired
	private ProfitDistributionRepositoryForSmallPackage profitDistributionRepositoryForSmallPackage;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	ReEntryProfitDistributionRepository reEntryProfitDistributionRepository;

	@Autowired
	PaymentDetailsRegistrationRepository paymentDetailsRegistrationRepository;

	@Value("${default.referral}")
	private String defaultReferral;

	@Value("${developer}")
	private String developer;

	@Value("${manufacturing}")
	private String manufacturing;

	@Value("${medical}")
	private String medical;

	@Value("${rewards}")
	private String rewards;

	@Value("${company}")
	private String company;

	@Value("${premiumuser}")
	private String premiumuser;

	@Value("${travel}")
	private String travel;

	@Value("${bike}")
	private String bike;

	@Value("${car}")
	private String car;

	@Value("${house}")
	private String house;

	@Value("${health}")
	private String health;

	@Value("${seminar}")
	private String seminar;

	@Autowired
	EmailService emailService;

	@Async("asyncExecutor")
	@Override
	@Transactional
	public void distributeRegistrationFee(User newUser) {
		String packageName = newUser.getPackageTaken().getName();
		Integer packageValue = Integer.parseInt(packageName);
		BigDecimal registrationFeeDecimal = new BigDecimal(packageValue);
		List<ProfitDistributionMasterSmallPackage> profitDistributions = profitDistributionRepositoryForSmallPackage
				.findAll();
		Map<String, BigDecimal> distributionMap = new HashMap<>();
		List<String> otherCompanyUser = new ArrayList<>();
		BigDecimal manufacturingPercentage = BigDecimal.ZERO;
		BigDecimal remainingFee = BigDecimal.ZERO;
		for (ProfitDistributionMasterSmallPackage profitDistribution : profitDistributions) {
			if ("manufacturing".equals(profitDistribution.getCategoryId())) {
				manufacturingPercentage = BigDecimal.valueOf(profitDistribution.getPercentage());
				BigDecimal manufacturingAllocation = registrationFeeDecimal.multiply(manufacturingPercentage)
						.divide(new BigDecimal(100));
				User manufacturingUser = userRepository.findByEmail("happyhomemanufacturing@gmail.com")
						.orElseThrow(() -> new RuntimeException("Manufacturing user not found"));
				updateUplineAccountBalance(manufacturingUser, manufacturingAllocation);
				remainingFee = registrationFeeDecimal.subtract(manufacturingAllocation);
			} else {
				BigDecimal percentageDecimal = BigDecimal.valueOf(profitDistribution.getPercentage());
				BigDecimal amount = remainingFee.multiply(percentageDecimal).divide(new BigDecimal(100));
				distributionMap.put(profitDistribution.getCategoryId(), amount);
				if (profitDistribution.getCategoryUserEmail() != null) {
					otherCompanyUser.add(profitDistribution.getCategoryUserEmail());
				}
			}
		}
		distributeRemainingToOtherUsers(otherCompanyUser, distributionMap);
		distributeToUpline(newUser, distributionMap);

	}

	private void distributeRemainingToOtherUsers(List<String> emails, Map<String, BigDecimal> distributionMap) {
		List<User> users = userRepository.findAllByEmailIn(emails);
		for (User user : users) {
			BigDecimal amount = null;
			switch (user.getEmail()) {
			case "happyhomedeveloper@gmail.com":
				amount = distributionMap.get("developer");
				break;
			case "happyhomemedical@gmail.com":
				amount = distributionMap.get("medical");
				break;
			case "happyhomerewards@gmail.com":
				amount = distributionMap.get("rewards");
				break;
			case "happyhomecompany@gmail.com":
				amount = distributionMap.get("company");
				break;
			case "happyhomepremiumuser@gmail.com":
				amount = distributionMap.get("premiumuser");
				break;
			case "happyhometraveluser@gmail.com":
				amount = distributionMap.get("travel");
				break;
			case "happyhomebikeuser@gmail.com":
				amount = distributionMap.get("bike");
				break;
			case "happyhomecaruser@gmail.com":
				amount = distributionMap.get("car");
				break;
			case "happyhomehouseuser@gmail.com":
				amount = distributionMap.get("house");
				break;
			case "happyhomehealthuser@gmail.com":
				amount = distributionMap.get("health");
				break;
			case "happyhomeseminaruser@gmail.com":
				amount = distributionMap.get("seminar");
				break;
			}
			if (amount != null) {
				BigDecimal currentBalance = user.getTotalAccountBalance() != null ? user.getTotalAccountBalance()
						: BigDecimal.ZERO;
				BigDecimal newBalance = currentBalance.add(amount);
				user.setTotalAccountBalance(newBalance);
				log.info("Updated balance for user: {} to amount: {}", user.getEmail(), amount);
			}
		}
		userRepository.saveAll(users);
	}

	private void distributeToUpline(User newUser, Map<String, BigDecimal> distributionMap) {
		User currentUser = newUser.getParent();
		int level = 1;
		String[] uplineLevels = { "sponsor", "l2", "l3", "l4", "l5", "l6", "l7", "l8" };
		while (currentUser != null && level <= uplineLevels.length) {
			BigDecimal commissionAmount = distributionMap.get(uplineLevels[level - 1]);
			updateUplineAccountBalance(currentUser, commissionAmount);
			emailService.sendCommissionEmailToUser(newUser.getFname() + " " + newUser.getLname(),
					newUser.getReferralCode(), commissionAmount, currentUser);
			currentUser = currentUser.getParent();
			if (currentUser == null) {
				User developerUser = userRepository.findIdByReferralCode(defaultReferral).get();
				currentUser = developerUser;
			}
			level++;
		}

	}

	private void updateUplineAccountBalance(User upline, BigDecimal amount) {
		try {

			if (upline != null && amount != null) {
				User managedUser = userRepository.findById(upline.getId()).orElse(null);
				if (managedUser != null) {
					BigDecimal currentBalance = managedUser.getTotalAccountBalance() != null
							? managedUser.getTotalAccountBalance()
							: BigDecimal.ZERO;
					BigDecimal newBalance = currentBalance.add(amount);
					log.info("Updating balance for user: {} from: {} to: {}", managedUser.getEmail(),
							managedUser.getTotalAccountBalance(), newBalance);
					managedUser.setTotalAccountBalance(newBalance);
					userRepository.save(managedUser);
				} else {
					log.warn("User not found for ID: {}", upline.getId());
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void reRegisterUser() {
		BigDecimal registrationFeeDecimal = new BigDecimal("10000");
		List<ReEntryProfitDistribution> profitDistributions = reEntryProfitDistributionRepository.findAll();
		Map<String, BigDecimal> distributionMap = new HashMap<>();
		List<String> otherCompanyUser = new ArrayList<>();
		for (ReEntryProfitDistribution profitDistribution : profitDistributions) {
			BigDecimal percentageDecimal = BigDecimal.valueOf(profitDistribution.getPercentage());
			BigDecimal amount = registrationFeeDecimal.multiply(percentageDecimal).divide(new BigDecimal(100));
			distributionMap.put(profitDistribution.getCategoryId(), amount);
			if (profitDistribution.getCategoryUserEmail() != null) {
				otherCompanyUser.add(profitDistribution.getCategoryUserEmail());
			}
		}

		BigInteger balanceThreshold = BigInteger.valueOf(110000);
		List<User> users = userRepository.findByTotalAccountBalanceGreaterThan(balanceThreshold);
		for (User user : users) {
			BigDecimal currentBalance = user.getTotalAccountBalance() != null ? user.getTotalAccountBalance()
					: BigDecimal.ZERO;
			BigDecimal amount = currentBalance.subtract(new BigDecimal("10000"));
			user.setTotalAccountBalance(amount);
			user.setUserLevel(UserLevel.EXPLORER);
			user = userRepository.save(user);
			distributeToUpline(user, distributionMap);
			distributeRemainingToOtherUsers(otherCompanyUser, distributionMap);

		}

	}

	@Override
	public ResponseEntity<Response<String>> updateRegistrationPaymentStatus(RegistrationPaymentRequest registration,
			MultipartFile[] file) {
		try {
			PaymentDetailsRegistration paymentDetailsRegistration = new PaymentDetailsRegistration();
			Set<RegistrationPaymentImage> images = uplodImage(file);
			paymentDetailsRegistration.setPaymentImages(images);

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public Set<RegistrationPaymentImage> uplodImage(MultipartFile[] multipartFiles) throws IOException {
		Set<RegistrationPaymentImage> imageModels = new HashSet<>();
		for (MultipartFile file : multipartFiles) {
			RegistrationPaymentImage imageModel = new RegistrationPaymentImage(file.getOriginalFilename(),
					file.getContentType(), file.getBytes());
			imageModels.add(imageModel);
		}
		return imageModels;
	}
}
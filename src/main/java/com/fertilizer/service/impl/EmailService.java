package com.fertilizer.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.fertilizer.enums.TemplateConstant;
import com.fertilizer.model.User;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;

	private static final String CHARSETNAME = "UTF-8";

	public void registrationEmail(User user) {
		String subject = "🎉 Get Started with Happy Home Products – Your Account is Ready!";
		String text = welcomeEmailTOUser(user, TemplateConstant.WELCOME_MAIL_TEMPLATE);
		sendEmail(user.getEmail(), subject, text);
	}

	private String welcomeEmailTOUser(User user, TemplateConstant orderConfirmationMail) {
		try {
			String template = getTemplateContent(TemplateConstant.WELCOME_MAIL_TEMPLATE);
			String emailContent = template.replace("{{username}}", user.getFname() + " " + user.getLname())
					.replace("{{referralCode}}", user.getReferralCode());
			return emailContent;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}

	public void sendEmail(String to, String subject, String text) {
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text, true);
			helper.setFrom("anujsinghhitsit@gmail.com");
			emailSender.send(message);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public String getTemplateContent(TemplateConstant templateConstant) throws IOException {
		try (InputStream inputStream = getClass().getResourceAsStream(templateConstant.getName());
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
			if (inputStream == null) {
				throw new IllegalArgumentException("Template not found: " + templateConstant.getName());
			}
			return reader.lines().collect(Collectors.joining(System.lineSeparator()));
		} catch (IOException e) {
			throw new RuntimeException("Error reading email template: " + templateConstant.name(), e);
		}
	}

	public void sendCommissionEmailToUser(String currentUserName, String currentReferral, BigDecimal amount,
			User user) {
		String subject = "🎉 Congratulations! You've Earned a Commission for a New Referral!";
		String text = comissionDistrubutionEmailToUser(currentUserName, currentReferral, amount, user,
				TemplateConstant.COMISSION_DISTRIBUTION);
		sendEmail(user.getEmail(), subject, text);
	}

	private String comissionDistrubutionEmailToUser(String currentUserName, String currentReferral, BigDecimal amount,
			User user, TemplateConstant orderConfirmationMail) {
		BigDecimal currentBalance = user.getTotalAccountBalance() != null ? user.getTotalAccountBalance()
				: BigDecimal.ZERO;
		BigDecimal newBalance = amount != null ? amount
				: BigDecimal.ZERO;
		try {
			String emailTemplate = getTemplateContent(TemplateConstant.COMISSION_DISTRIBUTION);
			String emailContent = emailTemplate
					.replace("{{username}}", user.getFname() + " " + user.getLname())
					.replace("{{totalAccountBalance}}", currentBalance.toString())
					.replace("{{commissionAmount}}", newBalance.toString())
					.replace("{{childName}}", currentUserName)
					.replace("{{childReferralCode}}", currentReferral);
			return emailContent;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}

}

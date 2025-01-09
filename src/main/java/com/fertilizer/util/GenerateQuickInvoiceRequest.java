package com.fertilizer.util;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Dhiraj
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class GenerateQuickInvoiceRequest {

	@JsonProperty(value = "customer_name")
	private String customerName;

	@JsonProperty(value = "customer_email_id")
	private String customerEmailId;

	@JsonProperty(value = "customer_email_subject")
	private String customerEmailSubject;

	@JsonProperty(value = "valid_for")
	private Long validFor;

	@JsonProperty(value = "valid_type")
	private String validType;

	private String currency;

	private String amount;

	@JsonProperty(value = "customer_mobile_no")
	private Long customerMobileNo;

	@JsonProperty(value = "bill_delivery_type")
	private String billDeliveryType;

	@JsonProperty(value = "merchant_reference_no")
	private String merchantReferenceNo;

	@JsonProperty(value = "terms_and_conditions")
	private String termsAndConditions;

	@JsonProperty(value = "invoice_description")
	private String invoiceDescription;

	@JsonProperty(value = "sms_content")
	private String smsContent;

	public GenerateQuickInvoiceRequest(String customerName, Long customerMobileNo, String customerEmailId,
			String customerEmailSubject, BigDecimal amount) {
		super();
		this.customerName = customerName;
		this.customerMobileNo = customerMobileNo;
		this.customerEmailId = customerEmailId;
		this.customerEmailSubject = customerEmailSubject;
		this.amount = amount.toPlainString();
	}

	@Override
	public String toString() {
		return "GenerateQuickInvoiceRequest [customerName=" + customerName + ", customerEmailId=" + customerEmailId
				+ ", customerEmailSubject=" + customerEmailSubject + ", validFor=" + validFor + ", validType="
				+ validType + ", currency=" + currency + ", amount=" + amount + ", customerMobileNo=" + customerMobileNo
				+ ", billDeliveryType=" + billDeliveryType + ", merchantReferenceNo=" + merchantReferenceNo
				+ ", termsAndConditions=" + termsAndConditions + ", invoiceDescription=" + invoiceDescription
				+ ", smsContent=" + smsContent + "]";
	}

}
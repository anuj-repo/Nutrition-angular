package com.fertilizer.payload.response;

import java.util.Date;

import com.fertilizer.enums.PaymentMode;
import com.fertilizer.enums.PaymentStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDetail {
	
	private Double voucherAmountWithTax;

	private String voucherCode;
	
	private Double pledgedAmount;
	
	private String tinyUrl;
	
	private String fnameInVoucher;
	
	private String lnameInVoucher;
	
	private String fname;
	
	private String lname;
	
	private String address;
	
	private String city;
	
	private String state;
	
	private String country;
	
	private String pinCode;
	
	private PaymentMode paymentMode;
	
	private Double voucherAmount;
	
	private PaymentStatus paymentStatus;
	
	private Date purchaseDate;
	
	private String invNumber;
	
	private String invoiceUrl;
	
	private Double gstPercent;
	
	private String pdfName;
	
	private Long userId;
	
	private String voucherTitle;
	
	private String compName;

	public PaymentDetail(Double voucherAmountWithTax, String voucherCode, Double pledgedAmount, String tinyUrl, String fname,String lname,String address, String city, String state,String pinCode,PaymentMode paymentMode,Double voucherAmount,PaymentStatus paymentStatus,Date purchaseDate, String invNumber,Double gstPercent,String pdfName,Long userId,String fnameInVoucher,String lnameInVoucher, String voucherTitle, String compName) {
		this.voucherAmountWithTax = voucherAmountWithTax;
		this.voucherCode = voucherCode;
		this.pledgedAmount = pledgedAmount;
		this.tinyUrl = tinyUrl;
		this.fname=fname;
		this.lname=lname;
		this.address=address;
		this.city=city;
		this.state=state;
		this.pinCode = pinCode;
		this.paymentMode =paymentMode;
		this.voucherAmount=voucherAmount;
		this.paymentStatus=paymentStatus;
		this.purchaseDate = purchaseDate;
		this.invNumber = invNumber;
		this.gstPercent = gstPercent;
		this.pdfName = pdfName;
		this.userId=userId;
		this.fnameInVoucher=fnameInVoucher;
		this.lnameInVoucher = lnameInVoucher;
		this.voucherTitle=voucherTitle;
		this.compName =  compName;
	}
}

package com.fertilizer.payload.response;

import java.util.List;

import com.fertilizer.enums.PaymentModeEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderInvoiceDTO {

	private String purchaseDate;
	private String invoiceNumber;
	private String name;
	private String address1;
	private String address2;
	private String gstNumber;//optionnal check
	private List<SubOrderInvoiceDTO> subOrders;
	private String subTotal; ////optionnal check | 0.0
	private String gst18; ////optionnal check | 0.0
	private PaymentModeEnum modeOfPayment;
	private String referenceNumber;///optionnal check | empty
	private String grandTotal; ////optionnal check | 0.0
	private String invoicePdfUrl;
	private String invoicePdfName;
	private String userId;

}

package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.PaymentRefundStatus;

@Converter(autoApply = true)
public class PaymentRefundStatusConverter implements AttributeConverter<PaymentRefundStatus, String> {

	@Override
	public String convertToDatabaseColumn(PaymentRefundStatus vehicle) {
		if (vehicle == null)
			return null;
		return vehicle.getName();
	}

	@Override
	public PaymentRefundStatus convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return PaymentRefundStatus.fromShortName(dbData);
	}
}

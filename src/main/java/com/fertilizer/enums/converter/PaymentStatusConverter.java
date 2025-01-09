package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.PaymentStatus;

@Converter(autoApply = true)
public class PaymentStatusConverter implements AttributeConverter<PaymentStatus, String> {

	@Override
	public String convertToDatabaseColumn(PaymentStatus vehicle) {
		if (vehicle == null)
			return null;
		return vehicle.getName();
	}

	@Override
	public PaymentStatus convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return PaymentStatus.fromShortName(dbData);
	}
}

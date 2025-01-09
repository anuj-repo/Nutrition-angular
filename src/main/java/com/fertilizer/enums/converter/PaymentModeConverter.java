package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.PaymentMode;

@Converter(autoApply = true)
public class PaymentModeConverter implements AttributeConverter<PaymentMode, String> {

	@Override
	public String convertToDatabaseColumn(PaymentMode vehicle) {
		if (vehicle == null)
			return null;
		return vehicle.getName();
	}

	@Override
	public PaymentMode convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return PaymentMode.fromShortName(dbData);
	}
}
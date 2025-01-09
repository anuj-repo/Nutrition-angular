package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.PaymentModeEnum;

@Converter(autoApply = true)
public class PaymentModeEnumConverter implements AttributeConverter<PaymentModeEnum, String> {

	@Override
	public String convertToDatabaseColumn(PaymentModeEnum vehicle) {
		if (vehicle == null)
			return null;
		return vehicle.getName();
	}

	@Override
	public PaymentModeEnum convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return PaymentModeEnum.fromShortName(dbData);
	}
}

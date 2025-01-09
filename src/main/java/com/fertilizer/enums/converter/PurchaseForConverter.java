package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.PurchaseFor;

@Converter(autoApply = true)

public class PurchaseForConverter implements AttributeConverter<PurchaseFor, String> {
	@Override
	public String convertToDatabaseColumn(PurchaseFor purchaseFor) {
		if (purchaseFor == null)
			return null;
		return purchaseFor.getName();
	}

	@Override
	public PurchaseFor convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return PurchaseFor.fromShortName(dbData);
	}
}

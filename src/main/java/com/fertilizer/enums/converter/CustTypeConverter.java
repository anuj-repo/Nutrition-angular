package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.CustType;

@Converter(autoApply = true)
public class CustTypeConverter implements AttributeConverter<CustType, String> {
	
	@Override
	public String convertToDatabaseColumn(CustType couponType) {
		if (couponType == null)
			return null;
		return couponType.getName();
	}

	@Override
	public CustType convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return CustType.fromShortName(dbData);
	}
}


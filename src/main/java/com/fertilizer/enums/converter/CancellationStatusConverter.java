package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.CancellationStatus;

@Converter(autoApply = true)
public class CancellationStatusConverter implements AttributeConverter<CancellationStatus, String> {

	@Override
	public String convertToDatabaseColumn(CancellationStatus status) {
		if (status == null)
			return null;
		return status.getName();
	}

	@Override
	public CancellationStatus convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return CancellationStatus.fromShortName(dbData);
	}
}

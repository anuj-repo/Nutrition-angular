package com.fertilizer.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fertilizer.enums.ApprovalStatus;
@Converter(autoApply = true)
public class ApprovalStatusConverter implements AttributeConverter<ApprovalStatus, String> {

	@Override
	public String convertToDatabaseColumn(ApprovalStatus attribute) {
		if (attribute == null)
			return null;

		return attribute.getName();
	}

	@Override
	public ApprovalStatus convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return ApprovalStatus.fromShortName(dbData);
	}

}
